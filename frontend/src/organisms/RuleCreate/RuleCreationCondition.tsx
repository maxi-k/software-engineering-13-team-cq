import React from 'react'
import { RuleCreationStepView, createSingleValueUpdater, createMultiValueUpdater, nestValueUpdater } from './common'
import PredicateCounter from '@/atoms/PredicateCounter'
import AddConditionButton from '@/atoms/AddConditionButton'
// import ConditionSelector from '@/atoms/ConditionSelector'
import { LogicalConnective, VehicleDataFieldType } from '@/model'
import { mapObjectToArray } from '@/utilities/collection-util'

const logicalConnectiveToSelectOption = (connective: LogicalConnective) => (
  {
    label: 'cns.predicate.counter.' + connective,
    value: connective
  }
)

const addConditionUpdater = (callback: ((...value: any) => void)) => (
  (event: React.SyntheticEvent<any, any>) => (
    callback('$add', {
      type: VehicleDataFieldType.String,
      fieldName: 'vehicleName'
    })
  )
)

const RuleCreationCondition: RuleCreationStepView = (
  { inProgressRule: { condition }, updateField }
) => {
  const logicalConnective = condition
    ? condition.logicalConnective
    : LogicalConnective.All;
  const nestedUpdater = nestValueUpdater(updateField)('condition')
  return (
    <div>
      <PredicateCounter
        onChange={createSingleValueUpdater(nestedUpdater)('logicalConnective')}
        value={logicalConnectiveToSelectOption(logicalConnective)}
        options={mapObjectToArray(LogicalConnective, (k, v) => logicalConnectiveToSelectOption(v))}
        beforeText="cns.predicate.counter.beforetext"
        afterText="cns.predicate.counter.aftertext"
      />
      <AddConditionButton
        onClick={addConditionUpdater(createMultiValueUpdater(nestedUpdater)('conditions'))}
      />
    </div>
  )
}

export default RuleCreationCondition
