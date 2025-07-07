package model.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthContext implements Serializable {

    @Field(type = FieldType.Keyword, name = "user_id")
    private String userId;

    @Field(type = FieldType.Keyword)
    private List<String> roles;

    @Field(type = FieldType.Boolean, name = "is_admin")
    private boolean isAdmin;

    @Field(type = FieldType.Boolean)
    private boolean authenticated;
}
