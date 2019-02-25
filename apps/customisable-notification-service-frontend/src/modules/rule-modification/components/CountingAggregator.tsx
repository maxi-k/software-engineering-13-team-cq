import React from 'react'

import InputField from '@/modules/shared/components/InputField'

import { AggregatorComponentAttributes } from '../aggregator-common'

const createAggregatorValueOnChange =
  (setter: AggregatorComponentAttributes['setAggregatorValue']): React.ChangeEventHandler<HTMLInputElement> => (
    (event: React.ChangeEvent<HTMLInputElement>) => (
      setter(event.target.value)
    )
  )

const CountingAggregator: React.SFC<AggregatorComponentAttributes> = ({
  aggregator, setAggregatorValue
}) => (
    <InputField
      label="cns.rule.field.aggregator.counting.label"
      value={(aggregator.value || "").toString()}
      onChange={createAggregatorValueOnChange(setAggregatorValue)} />
  )

export default CountingAggregator
