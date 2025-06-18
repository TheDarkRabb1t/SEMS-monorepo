package model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthContext implements Serializable {
    private String userId;
    private List<String> roles;
    private boolean isAdmin;
    private boolean authenticated;
}
