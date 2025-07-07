package model.dto;

import lombok.Data;

@Data
public class UserAgentInfoDto {
    private String browser;
    private String os;
    private String deviceType;
}