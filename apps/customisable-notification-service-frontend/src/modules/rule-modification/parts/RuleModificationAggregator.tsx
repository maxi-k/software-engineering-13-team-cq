import React from 'react'
import { mapArguments } from '@/utilities/function-util'
import {
  RuleModificationStepView,
  createDirectSingleValueUpdater,
  nestValueUpdater,
  createMergingValueUpdater
} from '../modification-common'
import AggregatorSelector from '../components/AggregatorSelector'
import { Aggregator, AggregatorStrategy } from '@/model';

const defaultAggregator: Aggregator = {
  strategy: AggregatorStrategy.Immediate
}

const mapStrategyUpdater = (value: any) => ({
  strategy: value,
  value: undefined
})

const RuleModificationAggregator: RuleModificationStepView = (
  { inProgressRule, updateField }
) => {
  const aggregatorUpdater = nestValueUpdater(updateField)('aggregator')
  return (
    <div>
      <AggregatorSelector
        aggregator={inProgressRule.aggregator || defaultAggregator}
        setAggregatorStrategy={mapArguments(
          createMergingValueUpdater(updateField)('aggregator'),
          mapStrategyUpdater
        )}
        setAggregatorValue={createDirectSingleValueUpdater(aggregatorUpdater)('value')} />
    </div>
  )
}

export default RuleModificationAggregator
