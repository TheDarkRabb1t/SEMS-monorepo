package model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeoLocation implements Serializable {
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
}
