import React from 'react'
import {
  RuleCreationStepView,
  FieldUpdater,
  createMergingValueUpdater,
  createSingleValueUpdater,
  nestValueUpdater
} from './common'
import {
  LogicalConnective,
  RuleCondition,
  RuleConditionPredicate,
} from '@/model'
import { createUUID } from '@/services/identifier-service'
import { mapObjectToArray } from '@/utilities/collection-util'

import PredicateCounter from '@/atoms/PredicateCounter'
import AddConditionButton from '@/atoms/AddConditionButton'
// import ConditionSelector from '@/atoms/ConditionSelector'

const logicalConnectiveToSelectOption = (connective: LogicalConnective) => (
  {
    label: 'cns.predicate.counter.' + connective,
    value: connective
  }
)

const addConditionUpdater = (callback: ((...value: any) => void)) => (
  (event: React.SyntheticEvent<any, any>) => (
    callback({ [createUUID()]: {} })
  )
)

const EditablePredicate: React.SFC<{
  predicate: Partial<RuleConditionPredicate<any>>,
  updateField: FieldUpdater
}> = ({ predicate, updateField }) => (
  <div>{JSON.stringify(predicate)}</div>
)

const PredicateList: React.SFC<{
  predicates: RuleCondition['predicates'],
  updateField: FieldUpdater
}> = (
  { predicates = {}, updateField }
) => (
      <>
        {mapObjectToArray(predicates, (key: string | number, predicate: RuleConditionPredicate<any>) => (
          <EditablePredicate key={key}
            predicate={predicate}
            updateField={nestValueUpdater(updateField)(key)} />
        ))
        }
      </>
    )

const RuleCreationCondition: RuleCreationStepView = (
  { inProgressRule: { condition }, updateField }
) => {
  const logicalConnective = condition.logicalConnective || LogicalConnective.All
  const predicates = condition.predicates || {}
  const conditionUpdater = nestValueUpdater(updateField)('condition')
  const predicatesUpdater = nestValueUpdater(conditionUpdater)('predicates')
  return (
    <div>
      <PredicateCounter
        onChange={createSingleValueUpdater(conditionUpdater)('logicalConnective')}
        value={logicalConnectiveToSelectOption(logicalConnective)}
        options={mapObjectToArray(LogicalConnective, (k, v) => logicalConnectiveToSelectOption(v))}
        beforeText="cns.predicate.counter.beforetext"
        afterText="cns.predicate.counter.aftertext"
      />
      <PredicateList
        predicates={predicates}
        updateField={predicatesUpdater}
      />
      <AddConditionButton
        onClick={addConditionUpdater(createMergingValueUpdater(conditionUpdater)('predicates'))}
      />
    </div>
  )
}

export default RuleCreationCondition
