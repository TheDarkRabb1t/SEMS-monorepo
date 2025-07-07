package model.dto;

import lombok.Data;

@Data
public class GeoLocationDto {
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
}