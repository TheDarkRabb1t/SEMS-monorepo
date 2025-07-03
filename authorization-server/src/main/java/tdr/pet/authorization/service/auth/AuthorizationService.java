package tdr.pet.authorization.service.auth;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import tdr.pet.authorization.model.dto.UserDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final Environment env;

    public Jwt generateToken(UserDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        Instant now = Instant.now();
        long expiry = 3600L;

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Extract user role and map to scopes
        String userRole = extractUserRole(authorities);
        Set<String> scopes = mapRoleToScopes(userRole);
        String scopeString = String.join(" ", scopes);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("%s://%s:%s".formatted(Boolean.parseBoolean(
                                env.getProperty("server.ssl.enabled")) ? "https" : "http",
                        env.getProperty("server.address"), env.getProperty("server.port")))
                .issuedAt(now)
                .expiresAt(now.plus(expiry, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("scope", scopeString)
                .claim("authorities", authorities)
                .claim("username", authentication.getName())
                .claim("user_role", userRole)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claimsSet));
    }

    private String extractUserRole(String authorities) {
        if (authorities.contains("ADMINISTRATOR")) {
            return "ADMIN";
        } else if (authorities.contains("USER")) {
            return "USER";
        }
        return "USER";
    }

    private Set<String> mapRoleToScopes(String role) {
        Set<String> scopes = new HashSet<>();

        switch (role) {
            case "ADMIN":
                scopes.add("ingest:read");
                scopes.add("ingest:write");
                scopes.add("admin:read");
                scopes.add("admin:write");
                break;
            case "USER":
                scopes.add("ingest:read");
                scopes.add("ingest:write");
                break;
            default:
                scopes.add("ingest:read");
                break;
        }

        return scopes;
    }
}