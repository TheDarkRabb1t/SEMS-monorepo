package model.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
public class UserAgentInfo implements Serializable {

    @Field(type = FieldType.Keyword)
    private String browser;

    @Field(type = FieldType.Keyword)
    private String os;

    @Field(type = FieldType.Keyword, name = "device_type")
    private String deviceType; // "Desktop", "Mobile", etc.
}