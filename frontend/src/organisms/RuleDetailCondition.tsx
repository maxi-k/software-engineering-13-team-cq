import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import Typography from '@material-ui/core/Typography'

import ErrorMessage from '@/atoms/ErrorMessage'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import NextButton from '@/atoms/NextButton'
import ClosingButton from '@/atoms/ClosingButton'
import PredicateCounter, { PredicateCounterProps } from '@/atoms/PredicateCounter'
import ConditionSelector, { ConditionSelectorProps } from '@/atoms/ConditionSelector'
import { PredicateCounterValue } from '@/model/Rule'

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
  options: [ PredicateCounterValue.All, PredicateCounterValue.Any, PredicateCounterValue.None ],
  beforeText: "cns.predicate.counter.beforetext",
  afterText: "cns.predicate.counter.aftertext"
}

const conditionSelectorProps: ConditionSelectorProps = {
  beforeText: "cns.condition.selector.beforetext",
  afterText: "cns.condition.selector.aftertext",
  dataTypeOptions: [
    { label: <FormattedMessage id = "cns.vehicle.status.battery.label" />, value: <FormattedMessage id = "cns.vehicle.status.battery.label" /> }
  ],
  comparisonTypeOptions: [
    { label: 'battery', value: 'above' }
  ]
}

const conditionFinisher = (finishCondition: FinishConditionType) =>
    (e: React.SyntheticEvent<any, any>): void =>
      finishCondition(e)

const conditionAborter = (abortCondition: AbortConditionType) =>
    (e: React.SyntheticEvent<any, any>): void =>
      abortCondition(e)

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
      <div style={{ paddingLeft: '1rem' }}>
        <PredicateCounter {... predicateCounterProps} />
      </div>
      <div style={{ paddingLeft: '1rem' }}>
        <ConditionSelector {... conditionSelectorProps} />
      </div>
      
      <div style={{ paddingLeft: '76rem' }}>
        <NextButton onClick={conditionFinisher(finishCondition)}/> 
      </div>
    </StyledRuleDetailCondition>
  )
}

export default RuleDetailCondition
