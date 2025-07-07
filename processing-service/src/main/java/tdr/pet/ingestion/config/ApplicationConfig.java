package tdr.pet.ingestion.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua_parser.Parser;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class ApplicationConfig {

    @Bean
    public DatabaseReader databaseReader(@Value("${geoip.dbPath}") String dbPath) throws IOException {
        return new DatabaseReader.Builder(Paths.get(dbPath).toFile()).build();
    }

    @Bean
    public Parser userAgentParser() {
        return new Parser();
    }
}
