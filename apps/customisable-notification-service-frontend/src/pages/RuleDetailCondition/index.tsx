import React from 'react'
import styled from 'styled-components'
import { push } from 'connected-react-router'
import { connect } from 'react-redux'
import { StateMapper, DispatchMapper } from '@/state/connector'
import { ruleDetailStateSelector } from '@/state/selectors'
import { loadRuleDetail } from '@/state/rule'
import { interpolatePagePath } from '@/pages/page-definitions'
import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import RuleDetailCondition, { FinishConditionType, AbortConditionType } from '@/modules/rule-detail/parts/RuleDetailCondition'

export interface RuleDetailConditionPageAttributes {
  // Needs to be string because it comes
  // from the router props
  parameters: {
    ruleId: string
  }
}

export interface StateAttributes extends FetchingAttributes {
  rule: NotificationRuleDetail
}

export interface DispatchAttributes {
  finishCondition: FinishConditionType,
  abortCondition: AbortConditionType,
  fetchRule(): void,
  editRule(): void,
  deleteRule(): void
}

export type RuleDetailConditionPageProps = RuleDetailConditionPageAttributes
  & StateAttributes
  & DispatchAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledPageWrapper = styled.div`
    flex-grow: 1;
`

class RuleDetailConditionPage extends React.PureComponent<RuleDetailConditionPageProps> {

  public componentDidMount = () => {
    const {fetchRule} = this.props
    fetchRule()
  }

  public render = () => {
    const {parameters, fetchRule, editRule, deleteRule, ...ruleDetailProps} = this.props
    return (
      <StyledPageWrapper>
        <RuleDetailCondition {...ruleDetailProps}/>
      </StyledPageWrapper>
    )
  }
}

const mapStateToProps: StateMapper<RuleDetailConditionPageAttributes, StateAttributes> = (state, props) => {
  const {rules, isFetching, hasFetchError} = ruleDetailStateSelector(state)
  return ({
    rule: rules[parseInt(props.parameters.ruleId, 10)],
    isFetching,
    hasFetchError
  })
}

const mapDispatchToProps: DispatchMapper<RuleDetailConditionPageAttributes, DispatchAttributes> = (dispatch, props) => ({
  finishCondition: (event) => dispatch(push(interpolatePagePath('TBD'))),
  abortCondition: (event) => dispatch(push(interpolatePagePath('ruleOverview'))),
  fetchRule: () => {
    dispatch(loadRuleDetail.request(parseInt(props.parameters.ruleId, 10)))
  },
  editRule: () => dispatch(push(interpolatePagePath('ruleEdit', props.parameters.ruleId))),
  deleteRule: () => alert('deleting rule')
})

export default connect(mapStateToProps, mapDispatchToProps)(RuleDetailConditionPage)
