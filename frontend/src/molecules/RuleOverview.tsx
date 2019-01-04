import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import AddRuleTile from '@/atoms/AddRuleTile'
import RuleTile from '@/atoms/RuleTile'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import ErrorMessage from '@/atoms/ErrorMessage'
import {
  NotificationRuleOverview as NotificationRule,
  FetchingAttributes, BasicHTMLProps
} from '@/model'

export type SelectRuleType = (event: React.SyntheticEvent<any, any>, rule: NotificationRule) => void
export interface RuleOverviewAttributes {
  rules: NotificationRule[],
  addRule(event: React.SyntheticEvent<any, any>): void,
  selectRule: SelectRuleType
}
export type RuleOverviewProps = RuleOverviewAttributes & FetchingAttributes & BasicHTMLProps

const StyledOverview = styled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: flex-start;
    padding: 1rem;
`

const defaultTileOptions = {
  padTile: 7
}

const ruleSelector = (selectRule: SelectRuleType, rule: NotificationRule) =>
  (e: React.SyntheticEvent<any, any>): void =>
    selectRule(e, rule)

const RuleOverview: React.SFC<RuleOverviewProps> = ({
  rules,
  addRule, selectRule,
  isFetching, hasFetchError,
  ...props }) => {

  if (hasFetchError) {
    return (
      <StyledOverview {...props}>
        <ErrorMessage message={
          <FormattedMessage id="cns.message.fetch.error" />
        } />
      </StyledOverview>
    )
  }
  if (isFetching) {
    return <LoadingIndicator isCentral={true} />
  }

  return (
    <StyledOverview {...props}>
      <AddRuleTile onClick={addRule} {...defaultTileOptions} />
      {rules.map((rule: NotificationRule) => (
        <RuleTile
          key={rule.id}
          {...defaultTileOptions}
          rule={rule}
          onClick={ruleSelector(selectRule, rule)} />
      ))}
    </StyledOverview>
  )

}

export default RuleOverview
