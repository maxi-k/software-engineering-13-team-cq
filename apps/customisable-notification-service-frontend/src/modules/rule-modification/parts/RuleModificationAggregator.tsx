import React from 'react'
import {
  RuleModificationStepView,
  createDirectSingleValueUpdater,
  nestValueUpdater
} from '../modification-common'
import AggregatorSelector from '../components/AggregatorSelector'
import { Aggregator, AggregatorStrategy } from '@/model';

const defaultAggregator: Aggregator = {
  strategy: AggregatorStrategy.Immediate
}

const RuleModificationAggregator: RuleModificationStepView = (
  { inProgressRule, updateField }
) => {
  const aggregatorUpdater = nestValueUpdater(updateField)('aggregator')
  return (
    <div>
      <AggregatorSelector
        aggregator={inProgressRule.aggregator || defaultAggregator}
        setAggregatorStrategy={createDirectSingleValueUpdater(aggregatorUpdater)('strategy')}
        setAggregatorValue={createDirectSingleValueUpdater(aggregatorUpdater)('value')} />
    </div>
  )
}

export default RuleModificationAggregator
