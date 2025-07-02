package tdr.pet.ingestion.service;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    private final WebClient webClient;

    @Value("${app.authorization-server.base-url}")
    private String authorizationServerUrl;

    public UserInfoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String extractUserIdFromAuth(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            String userId = extractUserIdFromJwt(jwt);

            if (userId == null && jwt.getClaimAsString("sub") != null) {
                try {
                    return fetchUserIdFromAuthServer(jwt.getTokenValue());
                } catch (Exception e) {
                    logger.warn("Failed to fetch user info from authorization server", e);
                }
            }
        }
        return "";
    }

    private String extractUserIdFromJwt(Jwt jwt) {
        String userId = jwt.getClaimAsString("user_id");
        if (userId != null) return userId;
        userId = jwt.getClaimAsString("preferred_username");
        if (userId != null) return userId;
        userId = jwt.getClaimAsString("username");
        if (userId != null) return userId;
        return jwt.getSubject();
    }

    private String fetchUserIdFromAuthServer(String token) {
        try {
            UserInfoResponse response = webClient.get()
                    .uri(authorizationServerUrl + "/api/users/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(UserInfoResponse.class)
                    .block();

            return response != null ? response.getUsername() : null;
        } catch (WebClientResponseException e) {
            logger.error("Error fetching user info: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to fetch user information", e);
        }
    }


    @Setter
    @Getter
    public static class UserInfoResponse {
        private String username;
        private String role;

    }
}