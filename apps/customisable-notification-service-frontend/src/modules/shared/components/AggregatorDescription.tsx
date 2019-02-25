import React from 'react'
import cronstrue from 'cronstrue/dist/cronstrue-i18n';
import { FormattedMessage, injectIntl, InjectedIntlProps } from 'react-intl'
import { AggregatorStrategy, Aggregator } from '@/model'

interface AggregatorDescriptionAttributes {
  aggregator: Aggregator
}

export type AggregatorDescriptionProps = AggregatorDescriptionAttributes
  & InjectedIntlProps
  & React.HTMLAttributes<HTMLDivElement>

const AggregatorDescription: React.SFC<AggregatorDescriptionProps> = ({ aggregator, intl, ...otherProps }) => {
  let Component = <div />
  switch (aggregator.strategy) {
    case AggregatorStrategy.Counting:
      Component = (
        <FormattedMessage id="cns.rule.field.aggregator.strategy.value.counting.shortDescription"
          values={{ count: aggregator.value }} />
      )
      break;
    case AggregatorStrategy.Scheduled:
      Component = (
        aggregator.value
          ? (<>
            <FormattedMessage id="cns.rule.field.aggregator.strategy.value.scheduled.shortDescription" />
            {': '}
            {cronstrue.toString((aggregator.value || '').toString(), { locale: intl.locale })}
          </>)
          : <FormattedMessage id="cns.rule.field.aggregator.strategy.value.scheduled.value.undefined.label" />
      )
      break;
    case AggregatorStrategy.Immediate:
    default:
      Component = (
        <FormattedMessage id="cns.rule.field.aggregator.strategy.value.immediate.shortDescription" />
      )
      break;
  }
  return Component
}

export default injectIntl(AggregatorDescription)
