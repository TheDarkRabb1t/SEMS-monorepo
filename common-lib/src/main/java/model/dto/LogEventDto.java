package model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class LogEventDto implements Serializable {
    private String ip;
    private String message;
    private String status;
    private String userAgent;
    private Instant timestamp;
}
