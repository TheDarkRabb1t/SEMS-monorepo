package tdr.pet.authorization.model.dto;

import lombok.Data;
import model.enums.UserRole;

@Data
public class UserDto {
    private String username;
    private String password;
    private UserRole role;
}
