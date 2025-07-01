CREATE TABLE IF NOT EXISTS oauth2_authorization
(
    id VARCHAR(100) PRIMARY KEY,
    registered_client_id          VARCHAR(100) NOT NULL,
    principal_name                VARCHAR(200) NOT NULL,
    authorization_grant_type      VARCHAR(100) NOT NULL,
    authorized_scopes             VARCHAR(1000),
    attributes                    BYTEA,
    state                         VARCHAR(500),
    authorization_code_value      BYTEA,
    authorization_code_issued_at  TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata   BYTEA,
    access_token_value            BYTEA,
    access_token_issued_at        TIMESTAMP,
    access_token_expires_at       TIMESTAMP,
    access_token_metadata         BYTEA,
    access_token_type             VARCHAR(100),
    access_token_scopes           VARCHAR(1000),
    oidc_id_token_value           BYTEA,
    oidc_id_token_issued_at       TIMESTAMP,
    oidc_id_token_expires_at      TIMESTAMP,
    oidc_id_token_metadata        BYTEA,
    refresh_token_value           BYTEA,
    refresh_token_issued_at       TIMESTAMP,
    refresh_token_expires_at      TIMESTAMP,
    refresh_token_metadata        BYTEA
);

CREATE INDEX IF NOT EXISTS idx_oauth2_auth_registered_client_id ON oauth2_authorization (registered_client_id);
CREATE INDEX IF NOT EXISTS idx_oauth2_auth_principal_name ON oauth2_authorization (principal_name);
CREATE INDEX IF NOT EXISTS idx_oauth2_auth_state ON oauth2_authorization (state);

CREATE TABLE IF NOT EXISTS oauth2_authorization_consent
(
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name       VARCHAR(200) NOT NULL,
    authorities          VARCHAR(1000),
    PRIMARY KEY (registered_client_id, principal_name)
);

CREATE INDEX IF NOT EXISTS idx_oauth2_consent_registered_client_id ON oauth2_authorization_consent (registered_client_id);
CREATE INDEX IF NOT EXISTS idx_oauth2_consent_principal_name ON oauth2_authorization_consent (principal_name);

CREATE TABLE IF NOT EXISTS oauth2_registered_client
(
    id VARCHAR(100) PRIMARY KEY,
    client_id                     VARCHAR(100)  NOT NULL,
    client_id_issued_at           TIMESTAMP     NOT NULL,
    client_secret                 VARCHAR(200),
    client_secret_expires_at      TIMESTAMP,
    client_name                   VARCHAR(200)  NOT NULL,
    client_authentication_methods VARCHAR(1000) NOT NULL,
    authorization_grant_types     VARCHAR(1000) NOT NULL,
    redirect_uris                 VARCHAR(1000),
    post_logout_redirect_uris     VARCHAR(1000),
    scopes                        VARCHAR(1000) NOT NULL,
    client_settings               VARCHAR(2000) NOT NULL,
    token_settings                VARCHAR(2000) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_oauth2_client_client_id ON oauth2_registered_client (client_id);
CREATE INDEX IF NOT EXISTS idx_oauth2_client_client_name ON oauth2_registered_client (client_name);

INSERT INTO oauth2_registered_client (id,
                                      client_id,
                                      client_id_issued_at,
                                      client_secret,
                                      client_secret_expires_at,
                                      client_name,
                                      client_authentication_methods,
                                      authorization_grant_types,
                                      redirect_uris,
                                      post_logout_redirect_uris,
                                      scopes,
                                      client_settings,
                                      token_settings)
SELECT 'admin-client-id',
       'admin-client',
       NOW(),
       'admin-secret',
       NULL,
       'Default Admin Client',
       'client_secret_basic',
       'authorization_code,refresh_token',
       'http://localhost:8080/login/oauth2/code/admin-client',
       'http://localhost:8080/',
       'openid,profile,email',
       '{"requireProofKey":false,"requireAuthorizationConsent":true}',
       '{"accessTokenTimeToLive":"PT1H","reuseRefreshTokens":true}'
WHERE NOT EXISTS (SELECT 1
                  FROM oauth2_registered_client
                  WHERE client_id = 'admin-client');
