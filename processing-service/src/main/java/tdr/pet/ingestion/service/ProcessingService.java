package tdr.pet.ingestion.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import model.EnrichedLogEvent;
import model.GeoLocation;
import model.LogEvent;
import model.UserAgentInfo;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class ProcessingService {
    private final DatabaseReader databaseReader;
    private final Parser userAgentParser;

    public ProcessingService(DatabaseReader databaseReader, Parser userAgentParser) {
        this.databaseReader = databaseReader;
        this.userAgentParser = userAgentParser;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "logs.queue", durable = "true"),
            exchange = @Exchange(value = "logs.exchange", ignoreDeclarationExceptions = "true", type = "topic"),
            key = "logs.key")
    )

    public void process(LogEvent logEvent) {
        EnrichedLogEvent enrichedLogEvent = EnrichedLogEvent.fromLogEvent(logEvent);
        enrichedLogEvent.setGeo(enrichGeoData(logEvent.getIp()));
        enrichedLogEvent.setParsedUserAgent(parseUserAgentInfo(logEvent.getUserAgent()));
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
        } catch (IOException | GeoIp2Exception e) {
            throw new RuntimeException(e);
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
