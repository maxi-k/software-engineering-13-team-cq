import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import {
  FetchingAttributes,
  NotificationRuleDetail,
  LogicalConnective,
  VehicleDataType,
  ComparisonType
} from '@/model'

import Typography from '@material-ui/core/Typography'

import ErrorMessage from '@/atoms/ErrorMessage'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import NextButton from '@/atoms/NextButton'
import ClosingButton from '@/atoms/ClosingButton'
import PredicateCounter, { PredicateCounterProps } from '@/atoms/PredicateCounter'
import ConditionSelector, { ConditionSelectorProps } from '@/atoms/ConditionSelector'
import AddConditionButton from '@/atoms/AddConditionButton';

export type FinishConditionType = (event: React.SyntheticEvent<any, any>) => void
export type AbortConditionType = (event: React.SyntheticEvent<any, any>) => void

export interface RuleDetailConditionAttributes {
  rule: NotificationRuleDetail
  finishCondition: FinishConditionType
  abortCondition: AbortConditionType
}

export type RuleDetailConditionProps = FetchingAttributes
  & RuleDetailConditionAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetailCondition = styled.div`
`

const predicateCounterProps: PredicateCounterProps = {
  value: { label: "cns.predicate.counter.all", value: "all" },
  onChange: (value: any) => (value),
  options: Object.values(LogicalConnective).map((predicateCounterValue) => (
    {
      label: `cns.predicate.counter.${predicateCounterValue.toLowerCase()}`,
      value: predicateCounterValue.toLowerCase()
    })),
  beforeText: "cns.predicate.counter.beforetext",
  afterText: "cns.predicate.counter.aftertext"
}

const conditionSelectorProps: ConditionSelectorProps = {
  beforeText: "cns.condition.selector.beforetext",
  afterText: "cns.condition.selector.aftertext",
  dataTypeValue: { label: "cns.vehicle.status.battery.label", value: "battery" },
  dataTypeOptions: Object.values(VehicleDataType).map((vehicleDataType) => (
    {
      label: `cns.vehicle.status.${vehicleDataType}.label`,
      options: [{
        label: `cns.vehicle.status.${vehicleDataType}.label`,
        value: vehicleDataType
      }]
    })),
  // tslint:disable-next-line:no-console
  onChangeDataType: (e: any) => console.log(e),
  comparisonTypeValue: { label: "cns.condition.selector.equalTo", value: "equal" },
  comparisonTypeOptions: Object.values(ComparisonType).map((comparisonType) => ({
    label: `cns.condition.selector.${comparisonType}`,
    value: comparisonType
  })),
  // tslint:disable-next-line:no-console
  onChangeComparisonType: (e: any) => console.log(e)
}

const StyledFieldSeparator = styled.div`
    padding: 1rem;
`

const TextElementWrapper = styled.div`
    paddingLeft: 1rem;
`

const conditionFinisher = (finishCondition: FinishConditionType) =>
  (event: React.SyntheticEvent<any, any>): void =>
    finishCondition(event)

const conditionAborter = (abortCondition: AbortConditionType) =>
  (event: React.SyntheticEvent<any, any>): void =>
    abortCondition(event)

const RuleDetailCondition: React.SFC<RuleDetailConditionProps> = ({
  isFetching, hasFetchError, rule, finishCondition, abortCondition, ...props
}) => {

  if (hasFetchError) {
    return (
      <ErrorMessage message={
        <FormattedMessage id="cns.message.fetch.error" />
      } />
    )
  }
  if (isFetching || typeof rule === 'undefined' || rule === null) {
    return <LoadingIndicator isCentral={true} />
  }

  return (
    <StyledRuleDetailCondition {...props}>
      <Typography variant="h5" style={{ paddingLeft: '1rem' }}>
        <FormattedMessage id="cns.rule.label" />{' '}
        "{rule.name}"
      </Typography>
      <div style={{ paddingLeft: '76rem' }}>
        <ClosingButton onClick={conditionAborter(abortCondition)} />
      </div>
      <StyledFieldSeparator />
      <TextElementWrapper>
        <FormattedMessage id="cns.page.ruleDetailCondition.question" />
      </TextElementWrapper>
      <TextElementWrapper>
        <PredicateCounter {...predicateCounterProps} />
      </TextElementWrapper>
      <TextElementWrapper>
        <ConditionSelector {...conditionSelectorProps} />
      </TextElementWrapper>
      <TextElementWrapper>
        <AddConditionButton onClick={conditionFinisher(finishCondition)} />
      </TextElementWrapper>
      <StyledFieldSeparator />
      <div style={{ paddingLeft: '76rem' }}>
        <NextButton onClick={conditionFinisher(finishCondition)} />
      </div>
    </StyledRuleDetailCondition>
  )
}

export default RuleDetailCondition
