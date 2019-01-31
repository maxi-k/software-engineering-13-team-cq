INSERT INTO user_settings_entity 
            (user_notification_type, 
             settings_id) 
VALUES      (0, 
             1) ON CONFLICT DO NOTHING;

INSERT INTO user_entity 
            (cell_phone_number, 
             mail_address, 
             user_settings_settings_id, 
             NAME) 
VALUES      (NULL, 
             NULL, 
             1, 
             'aaaaaaaa-0000-aaaa-0000-000000000001') ON CONFLICT DO NOTHING;

INSERT INTO aggregator_entity 
            (dtype, 
             aggregator_id) 
VALUES      ('AggregatorImmediateEntity', 
             2) ON CONFLICT DO NOTHING;

INSERT INTO rule_condition_entity 
            (logical_connective, 
             dtype, 
             condition_id) 
VALUES      (2, 
             'RuleConditionCompositeEntity', 
             3) ON CONFLICT DO NOTHING;

INSERT INTO rule_condition_entity 
            (comparison_type, 
             comparison_value, 
             field_name, 
             provider_name, 
             dtype, 
             condition_id) 
VALUES      (4, 
             10, 
             'voltage', 
             'battery', 
             'RuleConditionPredicateEntity', 
             4) ON CONFLICT DO NOTHING;

INSERT INTO rule_condition_entity 
            (comparison_type, 
             comparison_value, 
             field_name, 
             provider_name, 
             dtype, 
             condition_id) 
VALUES      (5, 
             10000, 
             'current', 
             'Mileage', 
             'RuleConditionPredicateEntity', 
             5) ON CONFLICT DO NOTHING;

INSERT INTO notification_rule_entity 
            (affecting_all_applicable_fleets, 
             aggregator_aggregator_id, 
             condition_condition_id, 
             description, 
             NAME, 
             fk_owner, 
             owner_as_additional_recipient, 
             rule_id) 
VALUES      (false, 
             2, 
             3, 
             'Some Description of this rule', 
             'First Testrule', 
             'aaaaaaaa-0000-aaaa-0000-000000000001', 
             true, 
             6) ON CONFLICT DO NOTHING;

INSERT INTO fleet_reference_entity 
            (fleet_id) 
VALUES      ('cccccccc-0000-ffff-0000-000000000001') ON CONFLICT DO NOTHING;

INSERT INTO fleet_reference_entity 
            (fleet_id) 
VALUES      ('cccccccc-0000-ffff-0000-000000000099') ON CONFLICT DO NOTHING;

INSERT INTO rule_condition_entity_sub_conditions 
            (rule_condition_composite_entity_condition_id, 
             sub_conditions_order, 
             sub_conditions_condition_id) 
VALUES      (3, 
             0, 
             4) ON CONFLICT DO NOTHING;

INSERT INTO rule_condition_entity_sub_conditions 
            (rule_condition_composite_entity_condition_id, 
             sub_conditions_order, 
             sub_conditions_condition_id) 
VALUES      (3, 
             1, 
             5) ON CONFLICT DO NOTHING;

INSERT INTO notification_rule_entity_affected_fleets 
            (notification_rule_entity_rule_id, 
             affected_fleets_order, 
             affected_fleets_fleet_id) 
VALUES      (6, 
             0, 
             'cccccccc-0000-ffff-0000-000000000001') ON CONFLICT DO NOTHING;

INSERT INTO notification_rule_entity_affected_fleets 
            (notification_rule_entity_rule_id, 
             affected_fleets_order, 
             affected_fleets_fleet_id) 
VALUES      (6, 
             1, 
             'cccccccc-0000-ffff-0000-000000000099') ON CONFLICT DO NOTHING;
