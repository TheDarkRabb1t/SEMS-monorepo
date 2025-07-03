package tdr.pet.authorization.service.auth;

import lombok.AllArgsConstructor;
import model.enums.UserRole;
import model.enums.UserScope;
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

        String userRole = extractUserRole(authorities);
        Set<UserScope> scopes = UserScope.getScopesForRole(UserRole.valueOf(userRole));
        String scopeString = scopes.stream().map(UserScope::toString).collect(Collectors.joining(" "));

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
        if (authorities.contains(UserRole.ADMIN.toString())) {
            return UserRole.ADMIN.toString();
        } else if (authorities.contains(UserRole.USER.toString())) {
            return UserRole.USER.toString();
        }
        return UserRole.USER.toString();
    }

}