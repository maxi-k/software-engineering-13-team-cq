import React from 'react'
import cronstrue from 'cronstrue/dist/cronstrue-i18n';
import { injectIntl, InjectedIntlProps, FormattedMessage } from 'react-intl'

import {
  StructuredCron,
  cronExpressionToStructuredCron,
  isValidCronExpression,
  structuredCronToCronExpression
} from '@/utilities/cron-util'

import Typography from '@material-ui/core/Typography';
import InputField from '@/modules/shared/components/InputField'

// import InputField from '@/modules/shared/components/InputField'

import { AggregatorComponentAttributes } from '../aggregator-common'

export type ScheduledAggregatorProps = AggregatorComponentAttributes & InjectedIntlProps
interface ScheduledAggregatorState {
  structuredCron: StructuredCron
}

class ScheduledAggregator extends React.Component<ScheduledAggregatorProps, ScheduledAggregatorState> {

  public static getDerivedStateFromProps = (props: ScheduledAggregatorProps, state: ScheduledAggregatorState) => {
    return {
      structuredCron: cronExpressionToStructuredCron(
        (props.aggregator.value || ScheduledAggregator.getDefaultCronExpression).toString(),
        ScheduledAggregator.getDefaultCronExpression()
      )
    }
  }

  private static getDefaultCronExpression = (): string => (
    "0 10 * * 1"
  )

  public state = {
    structuredCron: cronExpressionToStructuredCron(
      ScheduledAggregator.getDefaultCronExpression(),
      ScheduledAggregator.getDefaultCronExpression()
    )
  }

  public render = () => {
    // const { aggregator, setAggregatorValue } = this.props

    const { minute, hour /* month, dayOfMonth, dayOfWeek */ } = this.state.structuredCron

    return (
      <div>
        <InputField
          label="cns.rule.field.aggregator.scheduled.field.time.label"
          type="time"
          inputProps={{
            step: 900, // 15 minutes
          }}
          value={`${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`}
          onChange={this.updateTimeOfDay}
        />
        <Typography variant="body1" paragraph={true} style={{ paddingTop: '1rem' }}>
          <FormattedMessage id="cns.rule.field.aggregator.scheduled.description.label" />
          <br />
          <strong>
            {this.getHumanReadableCron()}
          </strong>
        </Typography>
      </div>
    )
  }

  /* Update functions */

  private updateTimeOfDay: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    const [hour, minute] = event.target.value
      .split(':')
      .map((value: string) => parseInt(value, 10))
      .map((numberOrNan: number) => isNaN(numberOrNan) ? 0 : numberOrNan)

    const updatedCron: StructuredCron = {
      ...this.state.structuredCron,
      hour,
      minute
    }
    this.updateUpstreamCronExpression(updatedCron)
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
    return structuredCronToCronExpression(this.state.structuredCron)
  }

  private updateUpstreamCronExpression = (structuredCron?: StructuredCron) => {
    const { setAggregatorValue } = this.props
    const cronExpression = typeof structuredCron === 'undefined' || structuredCron === null
      ? this.cronExpressionFromState()
      : structuredCronToCronExpression(structuredCron)
    setAggregatorValue(cronExpression)
  }

}

export default injectIntl(ScheduledAggregator)
