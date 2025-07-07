package tdr.pet.dashboard.controller;

import lombok.AllArgsConstructor;
import model.dto.EnrichedLogEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdr.pet.dashboard.service.EnrichedLogService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/enriched-logs")
public class EnrichedLogRestController {

    private final EnrichedLogService enrichedLogService;

    @PostMapping("/search")
    public ResponseEntity<Page<EnrichedLogEventDto>> searchEnrichedLogs(Pageable pageable, EnrichedLogEventDto enrichedLogEventDto) {
        return ResponseEntity.ok(enrichedLogService.searchByDto(enrichedLogEventDto, pageable));
    }
}
