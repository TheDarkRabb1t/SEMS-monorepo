package model;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class EnrichedLogEvent implements Serializable {
    private Instant timestamp;
    private String ip;
    private String message;
    private String rawUserAgent;
    private String userId;
    private String status;

    private GeoLocation geo;
    private UserAgentInfo parsedUserAgent;
    private SeverityLevel severity;
    private List<String> tags;

    private AuthContext auth;
}