package tdr.pet.ingestion.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.log4j.Log4j2;
import model.dto.LogEventDto;
import model.entity.EnrichedLogEvent;
import model.entity.GeoLocation;
import model.entity.UserAgentInfo;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tdr.pet.ingestion.repository.EnrichedLogEventRepository;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.IOException;
import java.net.InetAddress;

@Log4j2
@Service
public class ProcessingService {
    private final DatabaseReader databaseReader;
    private final Parser userAgentParser;
    private final EnrichedLogEventRepository enrichedLogEventRepository;

    public ProcessingService(DatabaseReader databaseReader, Parser userAgentParser, EnrichedLogEventRepository enrichedLogEventRepository) {
        this.enrichedLogEventRepository = enrichedLogEventRepository;
        this.userAgentParser = userAgentParser;
        this.databaseReader = databaseReader;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "logs.queue", durable = "true"),
            exchange = @Exchange(value = "logs.exchange", ignoreDeclarationExceptions = "true", type = "topic"),
            key = "logs.key")
    )
    public void process(LogEventDto logEventDto) {
        log.info("Enriching LogEvent:{}", logEventDto);
        EnrichedLogEvent enrichedLogEvent = EnrichedLogEvent.fromLogEvent(logEventDto);
        enrichedLogEvent.setGeo(enrichGeoData(logEventDto.getIp()));
        enrichedLogEvent.setParsedUserAgent(parseUserAgentInfo(logEventDto.getUserAgent()));
        log.info("Enriched LogEvent:{}", logEventDto);
        enrichedLogEventRepository.save(enrichedLogEvent);
        log.info("Saved EnrichedLogEvent:{}", logEventDto);
    }

    public GeoLocation enrichGeoData(String ip) {
        try {
            GeoLocation geoLocation = new GeoLocation();
            CityResponse cityResponse = databaseReader.city(InetAddress.getByName(ip));
            geoLocation.setCity(cityResponse.getCity().toString());
            geoLocation.setCountry(cityResponse.getCountry().toString());
            geoLocation.setLatitude(cityResponse.getLocation().getLatitude());
            geoLocation.setLongitude(cityResponse.getLocation().getLongitude());
            return geoLocation;
        } catch (AddressNotFoundException addressNotFoundException) {
            log.warn("Address not found. {}", addressNotFoundException.getMessage());
            return null;
        } catch (IOException | GeoIp2Exception e) {
            log.error("Error enriching GeoLocation", e);
            return null;
        }
    }

    public UserAgentInfo parseUserAgentInfo(String userAgentString) {
        Client client = userAgentParser.parse(userAgentString);
        UserAgentInfo userAgentInfo = new UserAgentInfo();
        userAgentInfo.setOs(client.device.family);
        userAgentInfo.setBrowser("%s/%s".formatted(client.userAgent.family, client.userAgent.patch));
        userAgentInfo.setDeviceType(client.device.family);
        return userAgentInfo;
    }
}
