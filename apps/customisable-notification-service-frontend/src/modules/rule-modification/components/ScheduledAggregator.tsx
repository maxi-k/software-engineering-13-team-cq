import React from 'react'
import cronstrue from 'cronstrue/dist/cronstrue-i18n';
import { injectIntl, InjectedIntlProps } from 'react-intl'

import {
  StructuredCron,
  cronExpressionToStructuredCron,
  isValidCronExpression,
  structuredCronToCronExpression
} from '@/utilities/cron-util'

// import InputField from '@/modules/shared/components/InputField'

import { AggregatorComponentAttributes } from '../aggregator-common'

export type ScheduledAggregatorProps = AggregatorComponentAttributes & InjectedIntlProps
interface ScheduledAggregatorState {
  structuredCron: StructuredCron
}

class ScheduledAggregator extends React.Component<ScheduledAggregatorProps, ScheduledAggregatorState> {

  private static getDefaultCronExpression = (): string => (
    "0 * * * 1"
  )

  public state = {
    structuredCron: cronExpressionToStructuredCron(
      ScheduledAggregator.getDefaultCronExpression(),
      ScheduledAggregator.getDefaultCronExpression()
    )
  }

  public render = () => {
    // const { aggregator, setAggregatorValue } = this.props

    return (
      <div onClick={this.updateUpstreamCronExpression}>
        {this.getHumanReadableCron()}
      </div>
    )
  }

  /* Helper functions */

  private getAggregatorCronExpression = (): string => {
    const aggregatorExpression = (this.props.aggregator.value || "").toString()
    if (!isValidCronExpression(aggregatorExpression)) {
      return ScheduledAggregator.getDefaultCronExpression()
    }
    return aggregatorExpression
  }

  private getHumanReadableCron = () => {
    const cronExpression = this.getAggregatorCronExpression().toString()
    const locale = this.props.intl.locale
    return cronstrue.toString(cronExpression, { locale })
  }

  private cronExpressionFromState = (): string => {
    // TODO
    return structuredCronToCronExpression(this.state.structuredCron)
  }

  private updateUpstreamCronExpression = () => {
    const { setAggregatorValue } = this.props
    setAggregatorValue(this.cronExpressionFromState())
  }

}

export default injectIntl(ScheduledAggregator)
