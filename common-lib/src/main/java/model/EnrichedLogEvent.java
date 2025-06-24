package model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class EnrichedLogEvent implements Serializable {
    @Id
    private String id;
    private String ip;
    private String message;
    private String rawUserAgent;
    private String userId;
    private String status;
    private Instant timestamp;

    private GeoLocation geo;
    private UserAgentInfo parsedUserAgent;
    private SeverityLevel severity;
    private List<String> tags;

    private AuthContext auth;

    public static EnrichedLogEvent fromLogEvent(LogEvent logEvent) {
        EnrichedLogEvent enrichedLogEvent = new EnrichedLogEvent();
        enrichedLogEvent.setIp(logEvent.getIp());
        enrichedLogEvent.setStatus(logEvent.getStatus());
        enrichedLogEvent.setMessage(logEvent.getMessage());
        enrichedLogEvent.setRawUserAgent(logEvent.getUserAgent());
        enrichedLogEvent.setTimestamp(logEvent.getTimestamp());
        return enrichedLogEvent;
    }
}