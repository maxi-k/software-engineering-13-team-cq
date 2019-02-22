CREATE TABLE vehicle_state_data_type_contract_entity_vins
  ( 
     vehicle_state_data_type_contract_entity_data_type_id INT8 NOT NULL, 
     vin                                                  VARCHAR(255) 
  ); 

CREATE TABLE vehicle_state_data_type_entity 
  ( 
     dtype         VARCHAR(255) NOT NULL,
     data_type_id  INT8 NOT NULL, 
     charge        FLOAT8, 
     status        VARCHAR(255), 
     voltage       FLOAT8, 
     calendar_week INT4, 
     due_per_week  INT4, 
     capacity      INT4, 
     fuel_type     VARCHAR(255), 
     power         INT4, 
     level         FLOAT8, 
     liters        INT4, 
     range         INT4, 
     CURRENT       INT4, 
     reached       INT4, 
     remaining     INT4, 
     brake_fluid   VARCHAR(255), 
     due_date      TIMESTAMP, 
     PRIMARY KEY (data_type_id) 
  ); 

CREATE TABLE vehicle_state_entity_vehicle_state_data_types 
  ( 
     vehicle_state_entity_state_id         INT8 NOT NULL, 
     vehicle_state_data_types_data_type_id INT8 NOT NULL, 
     PRIMARY KEY (vehicle_state_entity_state_id, 
     vehicle_state_data_types_data_type_id) 
  ); 

ALTER TABLE vehicle_state_entity_vehicle_state_data_types 
  ADD CONSTRAINT uk_f3euesnmd16sfw6kktp6txip9 UNIQUE ( 
  vehicle_state_data_types_data_type_id); 

ALTER TABLE vehicle_state_data_type_contract_entity_vins 
  ADD CONSTRAINT fkey6ooiyrvyg6s3yuml3gwtjov FOREIGN KEY ( 
  vehicle_state_data_type_contract_entity_data_type_id) REFERENCES 
  vehicle_state_data_type_entity; 

ALTER TABLE vehicle_state_entity 
  ADD CONSTRAINT fkdmju3ajwo1mfa9nu95ox2n87m FOREIGN KEY (fk_vehicle) REFERENCES 
  vehicle_reference_entity; 

ALTER TABLE vehicle_state_entity_vehicle_state_data_types 
  ADD CONSTRAINT fkkh34ttb9egqmw922erkyvt9ng FOREIGN KEY ( 
  vehicle_state_data_types_data_type_id) REFERENCES 
  vehicle_state_data_type_entity; 

ALTER TABLE vehicle_state_entity_vehicle_state_data_types 
  ADD CONSTRAINT fko3dg8ujpc4tayq3xhrkogsmbd FOREIGN KEY ( 
  vehicle_state_entity_state_id) REFERENCES vehicle_state_entity;
