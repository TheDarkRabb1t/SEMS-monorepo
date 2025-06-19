package tdr.pet.ingestion.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import model.EnrichedLogEvent;
import model.GeoLocation;
import model.LogEvent;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class ProcessingService {
    private final DatabaseReader databaseReader;

    public ProcessingService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "logs.queue", durable = "true"),
            exchange = @Exchange(value = "logs.exchange", ignoreDeclarationExceptions = "true"),
            key = "logs.key")
    )

    public void process(LogEvent logEvent) {
        EnrichedLogEvent enrichedLogEvent = new EnrichedLogEvent();
        enrichedLogEvent.setMessage(logEvent.getMessage());
        enrichedLogEvent.setStatus(logEvent.getStatus());
        enrichedLogEvent.setTimestamp(logEvent.getTimestamp());
        enrichedLogEvent.setGeo(enrichGeoData(logEvent.getIp()));
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
}
