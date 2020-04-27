--liquibase formatted sql
--changeset joosep.sisas:2020_04_25_init
CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY,
    email    TEXT UNIQUE NOT NULL,
    password TEXT        NOT NULL,
    fname    TEXT        NOT NULL,
    lname    TEXT        NOT NULL
);

CREATE TABLE user_token
(
    id                       BIGSERIAL PRIMARY KEY,
    access_token             TEXT      NOT NULL,
    access_token_expiration  TIMESTAMP NOT NULL,
    refresh_token            TEXT      NOT NULL,
    refresh_token_expiration TIMESTAMP NOT NULL,
    scope                    TEXT      NOT NULL,
    provider                 TEXT      NOT NULL,
    user_id                   BIGINT    NOT NULL REFERENCES "user" (id),
    unique (user_id, provider)
);

CREATE TABLE user_consent
(
    id        BIGSERIAL PRIMARY KEY,
    consent_id TEXT   NOT NULL,
    provider  TEXT   NOT NULL,
    user_id    BIGINT NOT NULL REFERENCES "user" (id),
    unique (user_id, provider)
);
