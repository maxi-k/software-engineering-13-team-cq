import React from 'react'
import { push } from 'connected-react-router'
import { interpolatePagePath } from '@/pages/page-definitions'
import { connect, StateMapper, DispatchMapper } from '@/state/connector'
import { ruleOverviewStateSelector } from '@/state/selectors'
import { loadRuleOverview, RuleOverviewState } from '@/state/rule'

import RuleOverview, { SelectRuleType } from '@/molecules/RuleOverview'
import RuleOverviewHeader from '@/organisms/RuleOverviewHeader'

import styled from 'styled-components'

export interface RuleOverviewDispatchProps {
  selectRule: SelectRuleType,
  fetchRules(): void,
  addRule(): void
}
export interface RuleOverviewStateProps extends RuleOverviewState {
}

export type RuleOverviewPageProps =
  RuleOverviewStateProps
  & RuleOverviewDispatchProps
  & React.HTMLAttributes<HTMLDivElement>

const StyledOverviewPage = styled.div`

`

class RuleOverviewPage extends React.Component<RuleOverviewPageProps> {

  public componentDidMount = () => {
    const { fetchRules } = this.props
    fetchRules()
  }

  public render = () => {
    const { rules, ...overviewProps } = this.props
    const ruleList = Object.values(rules)
    return (
      <StyledOverviewPage >
        <RuleOverviewHeader />
        <RuleOverview {...overviewProps} rules={ruleList} />
      </StyledOverviewPage >
    )
  }
}

const mapStateToProps: StateMapper<{}, RuleOverviewStateProps> = (state, ownProps) => ({
  ...ruleOverviewStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<{}, RuleOverviewDispatchProps> = (dispatch, props) => ({
  fetchRules: () => dispatch(loadRuleOverview.request()),
  selectRule: (event, rule) => dispatch(push(interpolatePagePath('ruleDetail', `${rule.ruleId}`))),
  addRule: () => dispatch(push(interpolatePagePath('ruleCreate')))
})

export default
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(RuleOverviewPage)
