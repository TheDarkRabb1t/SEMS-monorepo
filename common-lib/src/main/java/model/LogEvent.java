package model;

import java.time.Instant;

public record LogEvent(String message, Instant timestamp, String user, String ipAddress) {
}
