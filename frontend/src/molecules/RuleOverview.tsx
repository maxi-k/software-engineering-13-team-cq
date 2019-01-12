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
import { messageFromError } from '@/services/response-service'

export type SelectRuleType = (event: React.SyntheticEvent<any, any>, rule: NotificationRule) => void
export type AddRuleType = (event: React.SyntheticEvent<any, any>) => void
export interface RuleOverviewAttributes {
  rules: NotificationRule[],
  selectRule: SelectRuleType,
  addRule: AddRuleType
}
export type RuleOverviewProps = RuleOverviewAttributes & FetchingAttributes & BasicHTMLProps

const StyledOverview = styled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: flex-start;
    padding: 1rem;
`

export const defaultTilePadding = 7
const defaultTileOptions = {
  padTile: defaultTilePadding
}

const ruleSelector = (selectRule: SelectRuleType, rule: NotificationRule) =>
  (e: React.SyntheticEvent<any, any>): void =>
    selectRule(e, rule)

const RuleOverview: React.SFC<RuleOverviewProps> = ({
  addRule, selectRule,
  rules = [],
  isFetching = false,
  hasFetchError = false,
  ...props }) => {

  if (hasFetchError) {
    return (
      <StyledOverview {...props}>
        <ErrorMessage message={
          <FormattedMessage id={messageFromError(hasFetchError)} />
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
