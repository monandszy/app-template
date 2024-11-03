ALTER TABLE conversations
  ADD COLUMN created timestamptz DEFAULT NOW();

ALTER TABLE requests
  ADD COLUMN created timestamptz DEFAULT NOW(),
  ADD COLUMN text TEXT NOT NULL DEFAULT '';

ALTER TABLE responses
  ADD COLUMN text TEXT NOT NULL DEFAULT '';
--liquibase formatted sql