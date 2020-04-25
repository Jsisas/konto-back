--liquibase formatted sql
--changeset joosep.sisas:2020_04_25_init
CREATE TABLE "user"
(
    id    BIGSERIAL PRIMARY KEY,
    fname TEXT,
    lname TEXT
);

CREATE TABLE user_iban
(
    id     BIGSERIAL PRIMARY KEY,
    iban   TEXT,
    userId BIGINT REFERENCES "user" (id)
);
