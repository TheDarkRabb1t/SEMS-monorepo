package tdr.pet.authorization.config.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth.oauth2")
public class OAuth2ClientProperties {
    private List<Client> clients;

    @Data
    public static class Client {
        private String clientId;
        private String clientSecret;
        private List<String> grantTypes;
        private List<String> redirectUris;
        private List<String> postLogoutUris;
        private List<String> scopes;
        private boolean requireConsent;
    }
}
