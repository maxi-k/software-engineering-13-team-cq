import React from 'react'
import { push } from 'connected-react-router'
import { connect } from 'react-redux'

import { StateMapper, DispatchMapper } from '@/state/connector'
import { ruleDetailStateSelector } from '@/state/selectors'
import { loadRuleDetail } from '@/state/rule'
import { interpolatePagePath } from '@/pages/page-definitions'
import { FetchingAttributes, NotificationRuleDetail } from '@/model'
import RuleDetail from '@/organisms/RuleDetail'

export interface RuleDetailPageAttributes {
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
  fetchRule(): void,
  editRule(): void,
  deleteRule(): void
}

export type RuleDetailPageProps = RuleDetailPageAttributes
  & StateAttributes
  & DispatchAttributes
  & React.HTMLAttributes<HTMLDivElement>

class RuleDetailPage extends React.PureComponent<RuleDetailPageProps> {

  public componentDidMount = () => {
    const { fetchRule } = this.props
    fetchRule()
  }

  public render = () => {
    const { parameters, fetchRule, editRule, deleteRule, ...ruleDetailProps } = this.props
    return (
      <RuleDetail {...ruleDetailProps} />
    )
  }
}

const mapStateToProps: StateMapper<RuleDetailPageAttributes, StateAttributes> = (state, props) => {
  const { rules, isFetching, hasFetchError } = ruleDetailStateSelector(state)
  return ({
    rule: rules[parseInt(props.parameters.ruleId, 10)],
    isFetching,
    hasFetchError
  })
}

const mapDispatchToProps: DispatchMapper<RuleDetailPageAttributes, DispatchAttributes> = (dispatch, props) => ({
  fetchRule: () => {
    dispatch(loadRuleDetail.request(parseInt(props.parameters.ruleId, 10)))
  },
  editRule: () => dispatch(push(interpolatePagePath('ruleEdit', props.parameters.ruleId))),
  deleteRule: () => alert('deleting rule')
})

export default connect(mapStateToProps, mapDispatchToProps)(RuleDetailPage)
