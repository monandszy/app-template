CREATE TABLE IF NOT EXISTS catnip
(
    id uuid DEFAULT gen_random_uuid(),
    PRIMARY KEY (id)
);
--liquibase formatted sql