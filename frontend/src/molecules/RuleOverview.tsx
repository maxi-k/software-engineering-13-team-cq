import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import AddRuleTile from '@/atoms/AddRuleTile'
import RuleTile from '@/atoms/RuleTile'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import ErrorMessage from '@/atoms/ErrorMessage'
import {
  NotificationRule_Overview as NotificationRule,
  FetchingAttributes, BasicHTMLProps
} from '@/model'

export interface RuleOverviewAttributes {
  rules: NotificationRule[],
  addRule(event: React.SyntheticEvent<any, any>): void,
  selectRule(event: React.SyntheticEvent<any, any>, rule: NotificationRule): void
}
export type RuleOverviewProps = RuleOverviewAttributes & FetchingAttributes & BasicHTMLProps

const StyledOverview = styled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: flex-start;
    padding: 1rem;
`


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

  let tileOptions = {
    padTile: 7
  }
  return (
    <StyledOverview {...props}>
      <AddRuleTile onClick={addRule} {...tileOptions} />
      {rules.map((rule: NotificationRule) => (
        <RuleTile
          key={rule.id}
          {...tileOptions}
          rule={rule}
          onClick={(e: React.SyntheticEvent<any, any>) => selectRule(e, rule)} />
      ))}
    </StyledOverview>
  )

}

export default RuleOverview
