package model;

import java.io.Serializable;

public class UserAgentInfo implements Serializable {
    private String browser;
    private String os;
    private String deviceType; // "Desktop", "Mobile", etc.
}
