package tdr.pet.dashboard.config;

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
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api/enriched-logs/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
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

        // Extract scopes from 'scope' claim
        String scope = jwt.getClaimAsString("scope");
        if (scope != null && !scope.isEmpty()) {
            authorities.addAll(
                    Set.of(scope.split(" "))
                            .stream()
                            .filter(s -> !s.trim().isEmpty())
                            .map(s -> new SimpleGrantedAuthority("SCOPE_" + s))
                            .collect(Collectors.toSet())
            );
        }

        // Extract roles from 'authorities' claim
        String authoritiesString = jwt.getClaimAsString("authorities");
        if (authoritiesString != null && !authoritiesString.isEmpty()) {
            authorities.addAll(
                    Set.of(authoritiesString.split(" "))
                            .stream()
                            .filter(s -> !s.trim().isEmpty())
                            .map(s -> new SimpleGrantedAuthority("ROLE_" + s))
                            .collect(Collectors.toSet())
            );
        }

        // Extract user role and add as ROLE_
        String userRole = jwt.getClaimAsString("user_role");
        if (userRole != null && !userRole.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
        }

        return authorities;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
