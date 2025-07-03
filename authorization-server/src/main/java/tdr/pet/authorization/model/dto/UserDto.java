package tdr.pet.authorization.model.dto;

import lombok.Data;
import tdr.pet.authorization.model.UserRole;

@Data
public class UserDto {
    private String username;
    private String password;
    private UserRole role;
}
