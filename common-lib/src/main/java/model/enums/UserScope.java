package model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.Set;

public enum UserScope {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),

    USER_READ("user:read"),
    USER_WRITE("user:write"),

    INGEST_READ("ingest:read"),
    INGEST_WRITE("ingest:write");

    private final String value;

    UserScope(String value) {
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

    public static UserScope fromValue(String value) {
        for (UserScope userScopes : UserScope.values()) {
            if (userScopes.getValue().equals(value)) {
                return userScopes;
            }
        }
        throw new IllegalArgumentException("Unknown scope: " + value);
    }

    public static Set<UserScope> getScopesForRole(UserRole role) {
        return getDefaultRoleScopeMappings().get(role);
    }

    private static Map<UserRole, Set<UserScope>> getDefaultRoleScopeMappings() {
        return Map.of(
                UserRole.ADMIN, Set.of(
                        UserScope.INGEST_READ, UserScope.INGEST_WRITE,
                        UserScope.ADMIN_READ, UserScope.ADMIN_WRITE,
                        UserScope.USER_READ, UserScope.USER_WRITE
                ),
                UserRole.USER, Set.of(
                        UserScope.INGEST_READ, UserScope.INGEST_WRITE,
                        UserScope.USER_READ
                )
        );
    }
}