import React from 'react'
import styled from 'styled-components'
import { Link } from 'react-router-dom'
import { interpolatePagePath } from '@/pages/page-definitions'

export interface RuleEditPageAttributes {
  ruleId: string // needs to be a string because it's passed down by router
}

export type RuleEditPageProps = RuleEditPageAttributes & React.HTMLAttributes<HTMLDivElement>

const StyledRuleEditPage = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
`

const RuleEditPage: React.SFC<RuleEditPageProps> = ({ ruleId, ...props }) => (
  <StyledRuleEditPage {...props}>
    To be implemented <br />
    <Link to={interpolatePagePath('ruleOverview', ruleId)}>
      Back to Rule View
    </Link>
  </StyledRuleEditPage>
)

export default RuleEditPage
