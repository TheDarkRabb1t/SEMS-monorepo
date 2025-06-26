package tdr.pet.authorization.model.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import tdr.pet.authorization.model.UserRole;

import java.time.LocalDateTime;

@Data
@Table(name = "users")
public class CustomUser {
    @Id
    private String id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;
    @Column
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
