package tdr.pet.ingestion.controller;

import model.LogEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdr.pet.ingestion.service.LogPublisherService;

@RestController
@RequestMapping("api/logs")
public class LogRestController {
    private final LogPublisherService logPublisherService;

    public LogRestController(LogPublisherService logPublisherService) {
        this.logPublisherService = logPublisherService;
    }

    @PostMapping()
    public ResponseEntity<Void> uploadLogs(@RequestBody LogEvent logEvent) {
        logPublisherService.publishLog(logEvent);
        return ResponseEntity.accepted().build();
    }
}
