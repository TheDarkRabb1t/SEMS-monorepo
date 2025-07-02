-- Users table for authentication
CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     username VARCHAR(50) UNIQUE NOT NULL,
                                     password VARCHAR(100) NOT NULL,
                                     role VARCHAR(20) NOT NULL DEFAULT 'USER',
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- OAuth2 Authorization table (optimized)
CREATE TABLE IF NOT EXISTS oauth2_authorization (
                                                    id VARCHAR(100) PRIMARY KEY,
                                                    registered_client_id VARCHAR(100) NOT NULL,
                                                    principal_name VARCHAR(100) NOT NULL,
                                                    authorization_grant_type VARCHAR(50) NOT NULL,
                                                    authorized_scopes VARCHAR(500),
                                                    attributes BYTEA,
                                                    state VARCHAR(200),
                                                    authorization_code_value BYTEA,
                                                    authorization_code_issued_at TIMESTAMP,
                                                    authorization_code_expires_at TIMESTAMP,
                                                    authorization_code_metadata BYTEA,
                                                    access_token_value BYTEA,
                                                    access_token_issued_at TIMESTAMP,
                                                    access_token_expires_at TIMESTAMP,
                                                    access_token_metadata BYTEA,
                                                    access_token_type VARCHAR(50),
                                                    access_token_scopes VARCHAR(500),
                                                    oidc_id_token_value BYTEA,
                                                    oidc_id_token_issued_at TIMESTAMP,
                                                    oidc_id_token_expires_at TIMESTAMP,
                                                    oidc_id_token_metadata BYTEA,
                                                    refresh_token_value BYTEA,
                                                    refresh_token_issued_at TIMESTAMP,
                                                    refresh_token_expires_at TIMESTAMP,
                                                    refresh_token_metadata BYTEA
);

-- OAuth2 Authorization Consent table (optimized)
CREATE TABLE IF NOT EXISTS oauth2_authorization_consent (
                                                            registered_client_id VARCHAR(100) NOT NULL,
                                                            principal_name VARCHAR(100) NOT NULL,
                                                            authorities VARCHAR(500),
                                                            PRIMARY KEY (registered_client_id, principal_name)
);

-- OAuth2 Registered Client table (optimized)
CREATE TABLE IF NOT EXISTS oauth2_registered_client (
                                                        id VARCHAR(100) PRIMARY KEY,
                                                        client_id VARCHAR(100) NOT NULL UNIQUE,
                                                        client_id_issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                        client_secret VARCHAR(200),
                                                        client_secret_expires_at TIMESTAMP,
                                                        client_name VARCHAR(100) NOT NULL,
                                                        client_authentication_methods VARCHAR(500) NOT NULL,
                                                        authorization_grant_types VARCHAR(500) NOT NULL,
                                                        redirect_uris VARCHAR(1000),
                                                        post_logout_redirect_uris VARCHAR(500),
                                                        scopes VARCHAR(500) NOT NULL,
                                                        client_settings VARCHAR(1000) NOT NULL,
                                                        token_settings VARCHAR(1000) NOT NULL
);

-- Insert default admin user (password: 'admin')
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$0JKbgDmSDleX3UFa26RTFOKcJ57HbZzPBqA4NkzOnSka4seZbUV/.', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- Insert default regular user (password: 'user')
INSERT INTO users (username, password, role)
VALUES ('user', '$2a$10$aWEDjDCsb9VwMB0emb5wmeumgw4r9uCR7xZVlaVDomxEI.5YH7OUO', 'USER')
ON CONFLICT (username) DO NOTHING;