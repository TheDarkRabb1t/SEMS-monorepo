package tdr.pet.dashboard.controller;

import lombok.AllArgsConstructor;
import model.dto.EnrichedLogEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tdr.pet.dashboard.service.EnrichedLogService;

@AllArgsConstructor
@RestController("/api/enriched-logs")
public class EnrichedLogRestController {

    private final EnrichedLogService enrichedLogService;

    @GetMapping
    public ResponseEntity<Page<EnrichedLogEventDto>> getEnrichedLogs(Pageable pageable) {
        return ResponseEntity.ok(enrichedLogService.getEnrichedLogEvents(pageable));
    }

    @PostMapping
    public ResponseEntity<Page<EnrichedLogEventDto>> searchEnrichedLogs(EnrichedLogEventDto enrichedLogEventDto) {
        return ResponseEntity.ok(enrichedLogService.findEnrichedLogEventByDto(enrichedLogEventDto));
    }
}
