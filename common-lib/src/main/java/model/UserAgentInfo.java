package model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAgentInfo implements Serializable {
    private String browser;
    private String os;
    private String deviceType; // "Desktop", "Mobile", etc.
}
