import React from 'react'
import styled from 'styled-components'

import AddRuleTile from '@/atoms/AddRuleTile'
import RuleTile from '@/atoms/RuleTile'
import {
  NotificationRule_Overview as NotificationRule,
  FetchingAttributes, BasicHTMLProps
} from '@/model'

export interface RuleOverviewAttributes {
  rules: NotificationRule[],
  addRule(event: React.SyntheticEvent<any, any>): void,
  selectRule(event: React.SyntheticEvent<any, any>, rule: NotificationRule): void
}
type RuleOverviewProps = RuleOverviewAttributes & FetchingAttributes & BasicHTMLProps

const StyledOverview = styled.div`

`

const RuleOverview: React.SFC<RuleOverviewProps> = ({
  rules,
  addRule, selectRule,
  isFetching, hasFetchError,
  ...props }) => {

  if (isFetching) {

  }

  return (
    <StyledOverview {...props}>
      <AddRuleTile onClick={addRule} />
      {rules.map((rule: NotificationRule) => (
        <RuleTile rule={rule} onClick={(e: React.SyntheticEvent<any, any>) => selectRule(e, rule)} />
      ))}
    </StyledOverview>
  )

}

export default RuleOverview
