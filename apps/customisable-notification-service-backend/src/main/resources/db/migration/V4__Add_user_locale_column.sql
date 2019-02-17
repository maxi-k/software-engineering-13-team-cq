ALTER TABLE user_settings_entity
    ADD COLUMN locale int4;

UPDATE user_settings_entity
    SET locale = 1;

ALTER TABLE user_settings_entity ALTER COLUMN locale SET NOT NULL;
