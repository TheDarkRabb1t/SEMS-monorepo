package tdr.pet.ingestion.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import ua_parser.Parser;

import java.io.IOException;

@Configuration
public class ApplicationConfig {

    @Bean
    public DatabaseReader databaseReader(@Value("${geoip.dbPath}") Resource dbResource) throws IOException {
        return new DatabaseReader.Builder(dbResource.getInputStream()).build();
    }

    @Bean
    public Parser userAgentParser() {
        return new Parser();
    }
}
