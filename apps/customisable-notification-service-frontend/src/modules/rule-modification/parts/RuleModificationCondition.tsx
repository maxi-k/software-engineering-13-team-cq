import React from 'react'
import styled from 'styled-components'
import {
  RuleModificationStepView,
  FieldUpdater,
  createMergingValueUpdater,
  createSingleValueUpdater,
  nestValueUpdater
} from '../modification-common'
import {
  LogicalConnective,
  RuleCondition,
  RuleConditionPredicate,
  VehicleDataField
} from '@/model'
import { createRandomKey } from '@/services/identifier-service'
import { mapObjectToArray } from '@/utilities/collection-util'

import PredicateCounter from '../components/PredicateCounter'
import AddConditionButton from '../components/AddConditionButton'
import EditableRulePredicate from '../parts/EditableRulePredicate'

const logicalConnectiveToSelectOption = (connective: LogicalConnective) => (
  {
    label: 'cns.predicate.counter.' + connective,
    value: connective
  }
)

const addConditionUpdater = (callback: ((...value: any) => void)) => (
  (event: React.SyntheticEvent<any, any>) => (
    callback({ [createRandomKey()]: {} })
  )
)

const predicateVehicleDataFieldUpdater = (callback: ((...value: any) => void)) => (
  (field: VehicleDataField<any>) => (
    callback({
      appliedField: field,
      comparisonType: null
    }))
)

const predicateComparisonTypeUpdater = (callback: ((...value: any) => void)) => (
  (comparisonType: string) => (
    callback({ comparisonType })
  )
)

const predicateComparisonConstantUpdater = (callback: ((...value: any) => void)) => (
  (comparisonConstant: string) => (
    callback({ comparisonConstant })
  )
)

const predicateRemoveUpdater = (updater: FieldUpdater, key: string | number) => (
  (event: React.SyntheticEvent<any, any>) => (
    updater('$unset', () => [key])()
  )
)

const StyledPredicateList = styled.div`
    padding: 0.5rem 0 0 1rem;
`

const PredicateList: React.SFC<{
  predicates: RuleCondition['predicates'],
  updateField: FieldUpdater
}> = (
  { predicates = {}, updateField }
) => (
      <StyledPredicateList>
        {mapObjectToArray(predicates, (key: string | number, predicate: RuleConditionPredicate<any>) => {
          const updater = createMergingValueUpdater(updateField)(key)
          return (
            <EditableRulePredicate
              key={key}
              predicate={predicate}
              setVehicleDataField={predicateVehicleDataFieldUpdater(updater)}
              setComparisonType={predicateComparisonTypeUpdater(updater)}
              setComparisonConstant={predicateComparisonConstantUpdater(updater)}
              removeRule={predicateRemoveUpdater(updateField, key)}
            />
          )
        })

        }
      </StyledPredicateList>
    )

const RuleModificationCondition: RuleModificationStepView = (
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

export default RuleModificationCondition
