package model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthContextDto {
    private String userId;
    private List<String> roles;
    private boolean isAdmin;
    private boolean authenticated;
}