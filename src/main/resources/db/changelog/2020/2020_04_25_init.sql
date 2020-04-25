--liquibase formatted sql
--changeset joosep.sisas:2020_04_25_init
CREATE TABLE "user"
(
    id    BIGSERIAL PRIMARY KEY,
    email TEXT,
    password TEXT,
    fname TEXT,
    lname TEXT
);

CREATE TABLE user_iban
(
    id     BIGSERIAL PRIMARY KEY,
    iban   TEXT,
    userId BIGINT REFERENCES "user" (id)
);

CREATE TABLE user_token
(
    id                       BIGSERIAL PRIMARY KEY,
    access_token             TEXT,
    access_token_expiration  TIMESTAMP,
    refresh_token            TEXT,
    refresh_token_expiration TIMESTAMP,
    scope                    TEXT,
    provider                 TEXT,
    userId                   BIGINT REFERENCES "user" (id)
);