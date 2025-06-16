package tdr.pet.ingestion.controller;

import lombok.AllArgsConstructor;
import model.LogEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tdr.pet.ingestion.service.LogPublisherService;

@AllArgsConstructor
@RestController("/api/logs")
public class LogRestController {
    private final LogPublisherService logPublisherService;

    @PostMapping()
    public ResponseEntity<Void> uploadLogs(@RequestBody LogEvent logEvent) {
        logPublisherService.publishLog(logEvent);
        return ResponseEntity.accepted().build();
    }
}
