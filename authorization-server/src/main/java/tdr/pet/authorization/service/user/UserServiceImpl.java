package tdr.pet.authorization.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdr.pet.authorization.mapper.UserMapper;
import tdr.pet.authorization.model.UserRole;
import tdr.pet.authorization.model.dto.UserDto;
import tdr.pet.authorization.model.dto.UserRegistrationDto;
import tdr.pet.authorization.model.entity.CustomUser;
import tdr.pet.authorization.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserDto registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        CustomUser user = new CustomUser();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(UserRole.USER);

        CustomUser savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto getUserByUsername(String username) {
        CustomUser user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }
}
