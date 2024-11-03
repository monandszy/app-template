CREATE TABLE conversations
(
  id uuid DEFAULT gen_random_uuid(),
  account_id uuid,
  PRIMARY KEY (id),
  FOREIGN KEY (account_id) REFERENCES accounts (id)
);
CREATE TABLE requests
(
  id uuid DEFAULT gen_random_uuid(),
  conversation_id uuid NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (conversation_id) REFERENCES conversations (id)
);
CREATE TABLE responses
(
  id uuid DEFAULT gen_random_uuid(),
  request_id uuid NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (request_id) REFERENCES requests (id)
);
--liquibase formatted sql