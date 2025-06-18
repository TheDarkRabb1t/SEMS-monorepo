package model;

import java.io.Serializable;
import java.util.List;

public class EnrichedLogEvent implements Serializable {
    private String timestamp;
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