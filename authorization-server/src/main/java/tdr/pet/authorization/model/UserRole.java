package tdr.pet.authorization.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMINISTRATOR");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
