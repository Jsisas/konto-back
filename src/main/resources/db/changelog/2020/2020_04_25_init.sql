--liquibase formatted sql
--changeset joosep.sisas:2020_04_25_init
CREATE TABLE "user"
(
    id    BIGSERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    fname TEXT NOT NULL,
    lname TEXT NOT NULL
);

CREATE TABLE user_token
(
    id                       BIGSERIAL PRIMARY KEY,
    access_token             TEXT NOT NULL,
    access_token_expiration  TIMESTAMP NOT NULL,
    refresh_token            TEXT NOT NULL,
    refresh_token_expiration TIMESTAMP NOT NULL,
    scope                    TEXT NOT NULL,
    provider                 TEXT NOT NULL,
    userId                   BIGINT NOT NULL REFERENCES "user" (id),
    unique (userId, provider)
);