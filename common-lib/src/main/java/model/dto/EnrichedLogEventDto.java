package model.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class EnrichedLogEventDto {
    private String id;
    private String ip;
    private String message;
    private String rawUserAgent;
    private String userId;
    private String status;
    private Instant timestamp;
    private GeoLocationDto geo;
    private UserAgentInfoDto parsedUserAgent;
    private String severity;
    private List<String> tags;
    private AuthContextDto auth;
}
