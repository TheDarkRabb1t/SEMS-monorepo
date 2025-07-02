package tdr.pet.authorization.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdr.pet.authorization.model.dto.UserDto;
import tdr.pet.authorization.service.auth.AuthorizationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthorizationService authorizationService;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody UserDto loginRequest) {
        try {
            Jwt token = authorizationService.generateToken(loginRequest);
            Map<String, String> response = new HashMap<>();
            response.put("access_token", token.getTokenValue());
            response.put("token_type", "Bearer");
            response.put("expires_in", String.valueOf(token.getExpiresAt()));
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
