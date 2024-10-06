CREATE TABLE accounts
(
  id uuid DEFAULT gen_random_uuid(),
  email varchar(50) NOT NULL,
  password varchar(500) NOT NULL,
  enabled boolean NOT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX unique_email ON accounts(LOWER(email));

CREATE TABLE roles
(
  id uuid DEFAULT gen_random_uuid(),
  name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX unique_name ON roles(LOWER(name));

CREATE TABLE authorities
(
    account_id uuid NOT NULL,
    role_id uuid NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
CREATE UNIQUE INDEX ix_authorization ON authorities (account_id, role_id);
--liquibase formatted sql
-- https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html
-- TODO replace with centralized account storage.