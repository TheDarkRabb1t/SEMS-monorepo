package tdr.pet.ingestion;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("/api/logs")
public class LogRestController {
    @PostMapping()
    public void uploadLogs(@RequestParam("file") MultipartFile file) {

    }
}
