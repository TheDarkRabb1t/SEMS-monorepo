package tdr.pet.authorization.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdr.pet.authorization.model.dto.UserDto;
import tdr.pet.authorization.model.dto.UserRegistrationDto;
import tdr.pet.authorization.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        UserDto createdUser = userService.registerUser(registrationDto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        UserDto user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
}