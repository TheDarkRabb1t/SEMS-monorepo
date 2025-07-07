package tdr.pet.ingestion.config.init;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class GeoIPDownloader {

    @Value("${geoip.source.url}")
    private String url;
    @Value("${geoip.dbPath}")
    private String filePath;

    @PostConstruct
    public void downloadGeoIPDatabase() throws IOException {
        Path targetPath = Paths.get(filePath);
        if (!targetPath.toFile().exists()) {
            log.info("Downloading GeoIP database...");
            Files.createDirectories(targetPath.getParent());
            try (InputStream in = URI.create(url).toURL().openStream()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("Finished downloading GeoIP database.");
            }
        }
        log.info("GeoIP file found.");
    }
}
