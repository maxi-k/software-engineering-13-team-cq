import React from 'react'
import { connect } from 'react-redux'

import { StateMapper } from '@/state/connector'
import { predicateFieldListSelector } from '@/state/selectors'
import { PredicateFieldListState } from '@/state/predicate-field'
import { RuleConditionPredicate, SelectGroupedOptions, SelectValue, VehicleDataField } from '@/model'

import ConditionSelector from '@/atoms/ConditionSelector'
import { mapObjectToArray } from '@/utilities/collection-util';

export interface EditableRulePredicateAttributes {
  predicate: Partial<RuleConditionPredicate<any>>
}

interface StateAttributes {
  predicateFields: PredicateFieldListState
}

export type EditableRulePredicateProps =
  EditableRulePredicateAttributes
  & StateAttributes

const defaultFieldOptions: SelectGroupedOptions<SelectValue> = {
  label: 'cns.vehicle.status.unknown.label',
  options: []
}

const mapPredicateFieldOptions = (fields: PredicateFieldListState): Array<SelectGroupedOptions<SelectValue>> => (
  mapObjectToArray(fields, (vehicleDataType, vehiclePredicateField) => (
    typeof vehiclePredicateField === 'undefined' || vehiclePredicateField === null
      ? defaultFieldOptions
      : {
        label: `cns.vehicle.status.${vehicleDataType.toLowerCase()}.label`,
        options: vehiclePredicateField.predicateFields.map((predicateField) => (
          createPredicateFieldSelectValue({ predicateField, vehicleDataType })
        ))
      })
  )
)

const createPredicateFieldSelectValue = (vehicleDataField: VehicleDataField<any>): SelectValue => (
  {
    label: `cns.predicate.field.${vehicleDataField.vehicleDataType.toLowerCase()}.${vehicleDataField.predicateField.fieldName}.label`,
    value: vehicleDataField
  }
)

const EditableRulePredicate: React.SFC<EditableRulePredicateProps> = ({
  predicate, predicateFields
}) => {

  return (
    <ConditionSelector
      dataTypeOptions={mapPredicateFieldOptions(predicateFields)}
      dataTypeValue={typeof predicate.appliedField === 'undefined' || predicate.appliedField === null
        ? null
        : createPredicateFieldSelectValue(predicate.appliedField)}
      comparisonTypeOptions={[]}
      comparisonTypeValue={{ label: 'a', value: 'a' }}
      beforeText="cns.condition.selector.beforetext"
      afterText="cns.condition.selector.aftertext"
    />
  )
}

const mapStateToProps: StateMapper<EditableRulePredicateAttributes, StateAttributes> = (state, props) => ({
  predicateFields: predicateFieldListSelector(state)
})

export default connect(mapStateToProps)(EditableRulePredicate)
