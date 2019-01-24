import React from 'react'
import { connect } from 'react-redux'
import update from 'immutability-helper'

import { StateMapper } from '@/state/connector'
import { predicateFieldListSelector } from '@/state/selectors'
import { PredicateFieldListState } from '@/state/predicate-field'
import {
  SelectGroupedOptions,
  SelectValue,
  RuleConditionPredicate,
  VehicleDataField,
  PredicateField,
  SelectFormattedValue,
  SelectOnChangeType
} from '@/model'

import ConditionSelector from '@/atoms/ConditionSelector'
import { mapObjectToArray } from '@/utilities/collection-util';

export interface EditableRulePredicateAttributes {
  predicate: Partial<RuleConditionPredicate<any>>
  setVehicleDataField(vehicleDataField: VehicleDataField<any>): void
  setComparisonType(comparisonType: string): void,
  removeRule(event: React.SyntheticEvent<any, any>): void
  // TODO: Replicate possible comparison types as type?
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

const createComparisonTypeSelectValue = (comparisonType: string): SelectValue => (
  {
    label: `cns.predicate.comparison.${comparisonType}.label`,
    value: comparisonType
  }
)

const findPossibleComparisonTypes = (fieldName: string, predicateFields: Array<PredicateField<any>>): Set<string> => (
  predicateFields.reduce((options, predicateField) => (
    predicateField.fieldName === fieldName
      ? update(options, { $add: predicateField.possibleEvaluationStrategies })
      : options
  ), new Set())
)

const comparisonTypeChangeConverter = (callback: EditableRulePredicateAttributes['setComparisonType']): SelectOnChangeType<SelectFormattedValue | null> => (
  (selected) => (
    typeof selected !== 'undefined'
    && selected !== null
    && 'value' in selected
    && callback(selected.value as string)
  )
)

const vehicleDataChangeConverter = (callback: EditableRulePredicateAttributes['setVehicleDataField']): SelectOnChangeType<SelectFormattedValue | null> => (
  (selected) => (
    typeof selected !== 'undefined'
    && selected !== null
    && 'value' in selected
    && callback(selected.value as VehicleDataField<any>)
  )
)

const EditableRulePredicate: React.SFC<EditableRulePredicateProps> = ({
  predicate, predicateFields, setVehicleDataField, setComparisonType, removeRule
}) => {
  const applicableVehicleType =
    typeof predicate.appliedField === 'undefined' || predicate.appliedField === null
      ? null
      : predicateFields[predicate.appliedField.vehicleDataType]
  const comparisonTypes =
    typeof predicate.appliedField === 'undefined' || predicate.appliedField === null ||
      typeof applicableVehicleType === 'undefined' || applicableVehicleType === null
      ? new Set()
      : findPossibleComparisonTypes(predicate.appliedField.predicateField.fieldName,
        applicableVehicleType.predicateFields)

  const comparisonTypeOptions = Array.from(comparisonTypes).map(createComparisonTypeSelectValue)
  const comparisonTypeSelectValue =
    typeof predicate.comparisonType === 'undefined' || predicate.comparisonType === null
      ? null
      : createComparisonTypeSelectValue(predicate.comparisonType)

  return (
    <ConditionSelector
      dataTypeOptions={mapPredicateFieldOptions(predicateFields)}
      dataTypeValue={typeof predicate.appliedField === 'undefined' || predicate.appliedField === null
        ? null
        : createPredicateFieldSelectValue(predicate.appliedField)}
      onChangeDataType={vehicleDataChangeConverter(setVehicleDataField)}
      comparisonTypeOptions={comparisonTypeOptions}
      comparisonTypeValue={comparisonTypeSelectValue || (
        comparisonTypeOptions.length > 0
          ? comparisonTypeOptions[0]
          : null
      )}
      onChangeComparisonType={comparisonTypeChangeConverter(setComparisonType)}
      onClickRemove={removeRule}
      beforeText="cns.condition.selector.beforetext"
      afterText="cns.condition.selector.aftertext"
    />
  )
}

const mapStateToProps: StateMapper<EditableRulePredicateAttributes, StateAttributes> = (state, props) => ({
  predicateFields: predicateFieldListSelector(state)
})

export default connect(mapStateToProps)(EditableRulePredicate)
