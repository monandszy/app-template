CREATE TABLE conversations
(
  id uuid DEFAULT gen_random_uuid(),
  PRIMARY KEY (id)
);
CREATE TABLE edited_queries
(
  id uuid DEFAULT gen_random_uuid(),
  PRIMARY KEY (id)
);
CREATE TABLE queries
(
  id uuid DEFAULT gen_random_uuid(),
  PRIMARY KEY (id)
);
CREATE TABLE responses
(
  id uuid DEFAULT gen_random_uuid(),
  PRIMARY KEY (id)
);
--liquibase formatted sql