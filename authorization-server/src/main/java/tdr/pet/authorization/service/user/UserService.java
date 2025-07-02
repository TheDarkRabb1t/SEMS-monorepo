package tdr.pet.authorization.service.user;

import tdr.pet.authorization.model.dto.UserDto;
import tdr.pet.authorization.model.dto.UserRegistrationDto;

public interface UserService {
    UserDto registerUser(UserRegistrationDto registrationDto);

    UserDto getUserByUsername(String name);
}
