package model;

import java.io.Serializable;
import java.time.Instant;

public class LogEvent implements Serializable {
    private String ip;
    private String message;
    private String status;
    private String userAgent;
    private Instant timestamp;
}
