ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN charging_status VARCHAR(255);

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN remaining_charging_hours INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN remaining_range INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN end_mileage INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN end_date DATE;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN reached_runtime_percentage INT4;

ALTER TABLE vehicle_state_data_type_entity
    DROP COLUMN calendar_week;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN remaining_days INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN start_date DATE;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN start_mileage INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN transmission_type VARCHAR(255);

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN level_percentage FLOAT8;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN level_liters INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN tank_capacity FLOAT8;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN reached_percentage INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN average_per_week INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN expected_exceedance INT4;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN forecast_end_contract INT4;

ALTER TABLE vehicle_state_data_type_entity
    DROP COLUMN due_date;

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN due_date VARCHAR(255);

ALTER TABLE vehicle_state_data_type_entity
    ADD COLUMN remaining_mileage INT4;

ALTER TABLE vehicle_state_data_type_entity
    DROP COLUMN brake_fluid;

ALTER TABLE vehicle_reference_entity
    ADD COLUMN brand VARCHAR(255);

ALTER TABLE vehicle_reference_entity
    ADD COLUMN model VARCHAR(255);

ALTER TABLE vehicle_reference_entity
    ADD COLUMN series VARCHAR(255);

ALTER TABLE vehicle_reference_entity
    ADD COLUMN note VARCHAR(255);
