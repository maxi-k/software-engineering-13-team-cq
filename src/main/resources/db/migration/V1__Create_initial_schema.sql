CREATE TABLE aggregator_entity 
  ( 
     dtype                        VARCHAR(31) NOT NULL, 
     aggregator_id                INT8 NOT NULL, 
     notification_count_threshold INT4, 
     notification_cron_trigger    VARCHAR(255), 
     PRIMARY KEY (aggregator_id) 
  ); 

CREATE TABLE fleet_reference_entity 
  ( 
     fleet_id VARCHAR(255) NOT NULL, 
     PRIMARY KEY (fleet_id) 
  ); 

CREATE TABLE notification_rule_entity 
  ( 
     rule_id                         INT8 NOT NULL, 
     affecting_all_applicable_fleets BOOLEAN, 
     description                     VARCHAR(255), 
     name                            VARCHAR(255) NOT NULL, 
     owner_as_additional_recipient   BOOLEAN NOT NULL, 
     aggregator_aggregator_id        INT8, 
     condition_condition_id          INT8, 
     fk_owner                        VARCHAR(255), 
     PRIMARY KEY (rule_id) 
  ); 

CREATE TABLE notification_rule_entity_affected_fleets 
  ( 
     notification_rule_entity_rule_id INT8 NOT NULL, 
     affected_fleets_fleet_id         VARCHAR(255) NOT NULL 
  ); 

CREATE TABLE notification_rule_entity_recipients 
  ( 
     notification_rule_entity_rule_id INT8 NOT NULL, 
     recipients_recipient_id          INT8 NOT NULL 
  ); 

CREATE TABLE recipient_entity 
  ( 
     dtype        VARCHAR(31) NOT NULL, 
     recipient_id INT8 NOT NULL, 
     mail_address VARCHAR(255), 
     phone_number VARCHAR(255), 
     PRIMARY KEY (recipient_id) 
  ); 

CREATE TABLE rule_condition_entity 
  ( 
     dtype              VARCHAR(31) NOT NULL, 
     condition_id       INT8 NOT NULL, 
     logical_connective INT4, 
     comparison_type    INT4, 
     comparison_value   VARCHAR(255), 
     field_name         VARCHAR(255), 
     provider_name      VARCHAR(255), 
     PRIMARY KEY (condition_id) 
  ); 

CREATE TABLE rule_condition_entity_sub_conditions 
  ( 
     rule_condition_composite_entity_condition_id INT8 NOT NULL, 
     sub_conditions_condition_id                  INT8 NOT NULL, 
     sub_conditions_order                         INT4 NOT NULL, 
     PRIMARY KEY (rule_condition_composite_entity_condition_id, 
     sub_conditions_order) 
  ); 

CREATE TABLE user_entity 
  ( 
     name                      VARCHAR(255) NOT NULL, 
     cell_phone_number         VARCHAR(255), 
     mail_address              VARCHAR(255), 
     user_settings_settings_id INT8, 
     PRIMARY KEY (name) 
  ); 

CREATE TABLE user_settings_entity 
  ( 
     settings_id            INT8 NOT NULL, 
     user_notification_type INT4, 
     PRIMARY KEY (settings_id) 
  ); 

CREATE TABLE vehicle_reference_entity 
  ( 
     vin      VARCHAR(255) NOT NULL, 
     fk_fleet VARCHAR(255) NOT NULL, 
     PRIMARY KEY (vin) 
  ); 

CREATE TABLE vehicle_state_entity 
  ( 
     state_id   INT8 NOT NULL, 
     fk_vehicle VARCHAR(255) NOT NULL, 
     PRIMARY KEY (state_id) 
  ); 

ALTER TABLE notification_rule_entity_recipients 
  ADD CONSTRAINT recipient_id_unique UNIQUE (recipients_recipient_id);

ALTER TABLE rule_condition_entity_sub_conditions 
  ADD CONSTRAINT condition_id_unique UNIQUE (
  sub_conditions_condition_id); 

ALTER TABLE notification_rule_entity 
  ADD CONSTRAINT aggregator_entity_fk FOREIGN KEY (
  aggregator_aggregator_id) REFERENCES aggregator_entity; 

ALTER TABLE notification_rule_entity 
  ADD CONSTRAINT condition_id_fk FOREIGN KEY (condition_condition_id
  ) REFERENCES rule_condition_entity; 

ALTER TABLE notification_rule_entity 
  ADD CONSTRAINT user_entity_fk FOREIGN KEY (fk_owner) REFERENCES
  user_entity; 

ALTER TABLE notification_rule_entity_affected_fleets 
  ADD CONSTRAINT fleet_reference_entity_fk FOREIGN KEY (
  affected_fleets_fleet_id) REFERENCES fleet_reference_entity; 

ALTER TABLE notification_rule_entity_affected_fleets 
  ADD CONSTRAINT affected_fleets_notification_rule_entity_fk FOREIGN KEY (
  notification_rule_entity_rule_id) REFERENCES notification_rule_entity; 

ALTER TABLE notification_rule_entity_recipients 
  ADD CONSTRAINT recipient_entity_fk FOREIGN KEY (
  recipients_recipient_id) REFERENCES recipient_entity; 

ALTER TABLE notification_rule_entity_recipients 
  ADD CONSTRAINT recipients_notification_rule_entity_fk FOREIGN KEY (
  notification_rule_entity_rule_id) REFERENCES notification_rule_entity; 

ALTER TABLE rule_condition_entity_sub_conditions 
  ADD CONSTRAINT condition_sub_condition_fk FOREIGN KEY (
  sub_conditions_condition_id) REFERENCES rule_condition_entity; 

ALTER TABLE rule_condition_entity_sub_conditions 
  ADD CONSTRAINT condition_parent_condition_fk FOREIGN KEY (
  rule_condition_composite_entity_condition_id) REFERENCES rule_condition_entity 
; 

ALTER TABLE user_entity 
  ADD CONSTRAINT user_settings_fk FOREIGN KEY (
  user_settings_settings_id) REFERENCES user_settings_entity; 

ALTER TABLE vehicle_reference_entity 
  ADD CONSTRAINT vehicle_fleet_reference_fk FOREIGN KEY (fk_fleet) REFERENCES
  fleet_reference_entity; 

ALTER TABLE vehicle_state_entity 
  ADD CONSTRAINT state_vehicle_reference_fk FOREIGN KEY (fk_vehicle) REFERENCES
  vehicle_reference_entity; 

CREATE SEQUENCE hibernate_sequence INCREMENT 1 MINVALUE 1 MAXVALUE 
9223372036854775807 START 1 CACHE 1; 