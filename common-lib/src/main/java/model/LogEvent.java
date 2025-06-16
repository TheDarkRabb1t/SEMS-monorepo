package model;

import java.io.Serializable;
import java.time.Instant;

public record LogEvent(String message, Instant timestamp, String user, String ipAddress) implements Serializable {
}
