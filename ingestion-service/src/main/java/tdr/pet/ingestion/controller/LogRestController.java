package tdr.pet.ingestion.controller;

import lombok.AllArgsConstructor;
import model.dto.LogEventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdr.pet.ingestion.service.LogPublisherService;
import tdr.pet.ingestion.service.UserInfoService;

@RestController
@AllArgsConstructor
@RequestMapping("api/logs")
public class LogRestController {
    private final LogPublisherService logPublisherService;
    private final UserInfoService userInfoService;

    @PostMapping()
    public ResponseEntity<Void> uploadLogs(@RequestBody LogEventDto logEventDto, Authentication authentication) {
        logEventDto.setUserId(userInfoService.extractUserIdFromAuth(authentication));
        logPublisherService.publishLog(logEventDto);
        return ResponseEntity.accepted().build();
    }
}
