CREATE TABLE notification_rule_entity_matched_vehicle_states_not_yet_sent
  (
     notification_rule_entity_rule_id INT8 NOT NULL,
     matched_vehicle_states_not_yet_sent_state_id INT8 NOT NULL
  );

CREATE TABLE notification_rule_entity_processed_vehicle_states
  (
     notification_rule_entity_rule_id INT8 NOT NULL,
     processed_vehicle_states_state_id INT8 NOT NULL
  );

ALTER TABLE notification_rule_entity_matched_vehicle_states_not_yet_sent
  ADD CONSTRAINT notification_rule_matched_fk FOREIGN KEY (
  notification_rule_entity_rule_id) REFERENCES notification_rule_entity;

ALTER TABLE notification_rule_entity_matched_vehicle_states_not_yet_sent
  ADD CONSTRAINT vehicle_state_matched_fk FOREIGN KEY (
  matched_vehicle_states_not_yet_sent_state_id) REFERENCES vehicle_state_entity;

ALTER TABLE notification_rule_entity_processed_vehicle_states
  ADD CONSTRAINT notification_rule_processed_fk FOREIGN KEY (
  notification_rule_entity_rule_id) REFERENCES notification_rule_entity;

ALTER TABLE notification_rule_entity_processed_vehicle_states
  ADD CONSTRAINT vehicle_state_processed_fk FOREIGN KEY (
  processed_vehicle_states_state_id) REFERENCES vehicle_state_entity;

UPDATE fleet_reference_entity
    SET car_park_id = 'cccccccc-0000-cccc-0000-000000000099'
    WHERE fleet_id = 'cccccccc-0000-ffff-0000-000000000099';

UPDATE fleet_reference_entity
    SET car_park_id = 'cccccccc-0000-cccc-0000-000000000001'
    WHERE fleet_id = 'cccccccc-0000-ffff-0000-000000000001';

ALTER TABLE vehicle_state_entity
    ADD COLUMN created timestamp;

ALTER TABLE notification_rule_entity
    ADD COLUMN last_update timestamp;

UPDATE vehicle_state_entity
    SET created = '2019-01-20 19:10:25-10';

UPDATE notification_rule_entity
    SET last_update = '2019-01-22 19:10:25-07';
