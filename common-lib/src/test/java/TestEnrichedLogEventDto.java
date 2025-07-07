import model.entity.EnrichedLogEvent;
import model.dto.LogEventDto;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEnrichedLogEventDto {
    @Test
    void testFromLogEvent() {
        Instant now = Instant.now();

        LogEventDto event = new LogEventDto();
        event.setIp("127.0.0.1");
        event.setMessage("Hello World");
        event.setStatus("ERROR");
        event.setUserAgent("Mozilla/5.0");
        event.setTimestamp(now);

        EnrichedLogEvent enrichedLogEvent = EnrichedLogEvent.fromLogEvent(event);
        assertEquals("127.0.0.1", enrichedLogEvent.getIp());
        assertEquals("Hello World", enrichedLogEvent.getMessage());
        assertEquals("ERROR", enrichedLogEvent.getStatus());
        assertEquals("Mozilla/5.0", enrichedLogEvent.getRawUserAgent());
        assertEquals(now, enrichedLogEvent.getTimestamp());
    }
}