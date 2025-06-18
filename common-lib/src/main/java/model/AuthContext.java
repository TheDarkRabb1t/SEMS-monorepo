package model;

import java.io.Serializable;
import java.util.List;

public class AuthContext implements Serializable {
    private String userId;
    private List<String> roles;
    private boolean isAdmin;
    private boolean authenticated;
}
