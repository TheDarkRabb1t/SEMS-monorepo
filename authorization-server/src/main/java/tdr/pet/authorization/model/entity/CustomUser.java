package tdr.pet.authorization.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import tdr.pet.authorization.model.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "users")
public class CustomUser {
    @Id
    private UUID id;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("role")
    private UserRole role;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("modified_at")
    private LocalDateTime modifiedAt;

}
