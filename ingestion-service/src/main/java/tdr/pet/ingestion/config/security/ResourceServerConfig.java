package tdr.pet.ingestion.config.security;

import lombok.extern.slf4j.Slf4j;
import model.enums.UserRole;
import model.enums.UserScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api/logs").hasAnyAuthority(
                                "SCOPE_" + UserScope.INGEST_WRITE.getValue(),
                                "ROLE_" + UserRole.USER.getValue(),
                                "ROLE_" + UserRole.ADMIN.getValue()
                        )
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(extractScopesFromJWT(jwt));
        authorities.addAll(extractAuthoritiesFromJWT(jwt));
        authorities.add(extractUserRoleFromJWT(jwt));
        return authorities;
    }

    private GrantedAuthority extractUserRoleFromJWT(Jwt jwt) {
        String userRole = jwt.getClaimAsString("user_role");
        if (userRole != null && !userRole.isEmpty()) {
            try {
                UserRole role = UserRole.valueOf(userRole.toUpperCase());
                return new SimpleGrantedAuthority("ROLE_" + role.getValue());
            } catch (IllegalArgumentException e) {
                log.error("Unknown user_role in JWT: {}", userRole);
            }
        }
        return null;
    }

    private Set<GrantedAuthority> extractScopesFromJWT(Jwt jwt) {
        String scope = jwt.getClaimAsString("scope");
        if (scope != null && !scope.isEmpty()) {
            return Set.of(scope.split(" "))
                    .stream()
                    .filter(s -> !s.trim().isEmpty())
                    .map(s -> {
                        try {
                            UserScope.fromValue(s);
                            return new SimpleGrantedAuthority("SCOPE_" + s);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Unknown scope in JWT: " + s);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    private Set<GrantedAuthority> extractAuthoritiesFromJWT(Jwt jwt) {
        String authoritiesString = jwt.getClaimAsString("authorities");
        if (authoritiesString != null && !authoritiesString.isEmpty()) {
            return Set.of(authoritiesString.split(" "))
                    .stream()
                    .filter(s -> !s.trim().isEmpty())
                    .map(s -> {
                        try {
                            UserRole role = UserRole.valueOf(s.toUpperCase());
                            return new SimpleGrantedAuthority("ROLE_" + role.getValue());
                        } catch (IllegalArgumentException e) {
                            System.err.println("Unknown role in JWT authorities: " + s);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}