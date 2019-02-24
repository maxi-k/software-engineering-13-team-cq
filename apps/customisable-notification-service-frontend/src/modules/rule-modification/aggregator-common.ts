import { Aggregator } from '@/model'

export interface AggregatorComponentAttributes {
  aggregator: Aggregator,
  setAggregatorValue: (aggregatorValue: string) => void
}
