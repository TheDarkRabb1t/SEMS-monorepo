package tdr.pet.ingestion.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ApplicationConfig {
    @Value("${geoip.dbPath}")
    private String cityMmdbPath;

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        return new DatabaseReader.Builder(getClass().getResourceAsStream(this.cityMmdbPath)).build();
    }
}
