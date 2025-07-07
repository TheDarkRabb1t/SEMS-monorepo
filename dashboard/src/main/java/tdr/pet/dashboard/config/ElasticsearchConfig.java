package tdr.pet.dashboard.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${ELASTIC_SEARCH_SCHEME:http}")
    private String scheme;

    @Value("${ELASTIC_SEARCH_HOST}")
    private String host;

    @Value("${ELASTIC_SEARCH_PORT}")
    private int port;

    @Value("${ELASTIC_SEARCH_USER}")
    private String username;

    @Value("${ELASTIC_SEARCH_PASS}")
    private String password;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, scheme))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credsProvider)
                );

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        RestClientTransport transport = new RestClientTransport(
                builder.build(),
                new JacksonJsonpMapper(objectMapper)
        );

        return new ElasticsearchClient(transport);
    }
}
