import React from 'react'
import cronstrue from 'cronstrue/dist/cronstrue-i18n';
import { injectIntl, InjectedIntlProps, FormattedMessage } from 'react-intl'
import { defaultSelectStyles } from '@/utilities/style-util'

import {
  StructuredCron,
  cronExpressionToStructuredCron,
  isValidCronExpression,
  structuredCronToCronExpression
} from '@/utilities/cron-util'
import { SelectOnChangeType, SelectFormattedValue } from '@/model/Component'

import { withTheme, WithTheme } from '@material-ui/core/styles'
import Switch from '@material-ui/core/Switch'
import Typography from '@material-ui/core/Typography';
import InputField from '@/modules/shared/components/InputField'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import FormLabel from '@material-ui/core/FormLabel'
import Select from 'react-select'

// import InputField from '@/modules/shared/components/InputField'

import { AggregatorComponentAttributes } from '../aggregator-common'

export type ScheduledAggregatorProps = AggregatorComponentAttributes
  & InjectedIntlProps
  & WithTheme
interface ScheduledAggregatorState {
  structuredCron: StructuredCron
}

class ScheduledAggregator extends React.Component<ScheduledAggregatorProps, ScheduledAggregatorState> {

  public static getDerivedStateFromProps = (props: ScheduledAggregatorProps, state: ScheduledAggregatorState) => {
    return {
      structuredCron: cronExpressionToStructuredCron(
        (typeof props.aggregator.value !== 'undefined' && props.aggregator.value !== null
          ? props.aggregator.value
          : ScheduledAggregator.getDefaultCronExpression()).toString(),
        ScheduledAggregator.getDefaultCronExpression()
      )
    }
  }

  private static getDefaultCronExpression = (): string => (
    "0 10 ? * 1"
  )

  public state = {
    structuredCron: cronExpressionToStructuredCron(
      ScheduledAggregator.getDefaultCronExpression(),
      ScheduledAggregator.getDefaultCronExpression()
    )
  }

  public render = () => {
    // const { aggregator, setAggregatorValue } = this.props

    const { minute, hour, month, /* dayOfMonth, */ dayOfWeek } = this.state.structuredCron
    const selectStyle = defaultSelectStyles(this.props.theme)

    return (
      <div>
        <div>
          <FormControlLabel
            control={
              <Switch
                checked={month === '*'}
                onChange={this.updateSendEveryMonth}
                value="sendRulesEveryMonth" />
            }
            label={
              <FormattedMessage id="cns.rule.field.aggregator.scheduled.field.sendEveryMonth.label" />
            }
          />
          {month !== '*' && (
            <>
              <FormLabel>
                <p>
                  <FormattedMessage id="cns.rule.field.aggregator.scheduled.field.month.label" />
                </p>
              </FormLabel>
              <Select
                value={typeof month === 'object'
                  ? month.map(this.mapSelectMonthValue)
                  : this.mapSelectMonthValue(month)}
                options={this.getMonthSelectValues()}
                onChange={this.updateMonths}
                styles={selectStyle}
                closeMenuOnSelect={false}
                isMulti />
            </>
          )
          }
        </div>
        <div>
          <FormControlLabel
            control={
              <Switch
                checked={dayOfWeek === '*'}
                onChange={this.updateSendEveryWeekday}
                value="sendRulesEveryWeekday" />
            }
            label={
              <FormattedMessage id="cns.rule.field.aggregator.scheduled.field.sendEveryWeekday.label" />
            }
          />
          {dayOfWeek !== '*' && (
            <>
              <FormLabel>
                <p>
                  <FormattedMessage id="cns.rule.field.aggregator.scheduled.field.weekday.label" />
                </p>
              </FormLabel>
              <Select
                value={typeof dayOfWeek === 'object'
                  ? dayOfWeek.map(this.mapSelectWeekdayValue)
                  : this.mapSelectWeekdayValue(dayOfWeek)}
                options={this.getWeekdaySelectValues()}
                onChange={this.updateDayOfWeek}
                styles={selectStyle}
                closeMenuOnSelect={false}
                isMulti />
            </>
          )
          }
        </div>
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

  private updateSendEveryMonth = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newCronValue = event.target.checked
      ? '*'
      : 1 // Default value: January
    const updatedCron: StructuredCron = {
      ...this.state.structuredCron,
      month: newCronValue
    }
    this.updateUpstreamCronExpression(updatedCron)
  }

  private updateMonths: SelectOnChangeType<SelectFormattedValue> = (diff, action) => {
    const months = typeof diff === 'undefined' || diff === null
      ? ['*']
      : (Array.isArray(diff)
        ? diff.map((month) => month.value)
        : diff.value
      )
    const updatedCron: StructuredCron = {
      ...this.state.structuredCron,
      month: months.join(',')
    }
    this.updateUpstreamCronExpression(updatedCron)
  }

  private updateSendEveryWeekday = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newCronValue = event.target.checked
      ? '*'
      : 1 // Default value: Monday
    const updatedCron: StructuredCron = {
      ...this.state.structuredCron,
      dayOfWeek: newCronValue
    }
    this.updateUpstreamCronExpression(updatedCron)
  }

  private updateDayOfWeek: SelectOnChangeType<SelectFormattedValue> = (diff, action) => {
    const weekdays = typeof diff === 'undefined' || diff === null
      ? ['*']
      : (Array.isArray(diff)
        ? diff.map((weekday) => weekday.value)
        : diff.value
      )
    const updatedCron: StructuredCron = {
      ...this.state.structuredCron,
      dayOfWeek: weekdays.join(',')
    }
    this.updateUpstreamCronExpression(updatedCron)
  }

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
  private mapSelectMonthValue = (month: number | string): SelectFormattedValue => ({
    label: <FormattedMessage id="cns.constants.month.label" values={{ month }} />,
    value: month,
    key: month
  })

  private getMonthSelectValues = (): SelectFormattedValue[] => (
    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].map(this.mapSelectMonthValue)
  )

  private mapSelectWeekdayValue = (weekday: number | string): SelectFormattedValue => ({
    label: <FormattedMessage id="cns.constants.weekday.label" values={{ weekday }} />,
    value: weekday,
    key: weekday
  })

  private getWeekdaySelectValues = (): SelectFormattedValue[] => (
    [0, 1, 2, 3, 4, 5, 6].map(this.mapSelectWeekdayValue)
  )

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

export default withTheme()(injectIntl(ScheduledAggregator))
