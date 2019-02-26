import React from 'react'
import { push } from 'connected-react-router'
import { connect } from 'react-redux'
import { injectIntl, InjectedIntlProps } from 'react-intl'

import { StateMapper, DispatchMapper } from '@/state/connector'
import { ruleDetailStateSelector } from '@/state/selectors'
import { loadRuleDetail, deleteRemoteRule } from '@/state/rule'
import { interpolatePagePath } from '@/pages/page-definitions'
import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import RuleDetail from '@/modules/rule-detail/views/RuleDetail'

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
  editRule(rule: NotificationRuleDetail): void,
  deleteRule(rule: NotificationRuleDetail): void
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
      <RuleDetail
        {...ruleDetailProps}
        toggleEditRule={editRule}
        toggleDeleteRule={deleteRule} />
    )
  }
}

const mapStateToProps: StateMapper<RuleDetailPageAttributes & InjectedIntlProps, StateAttributes> = (state, props) => {
  const { rules, isFetching, hasFetchError } = ruleDetailStateSelector(state)
  return ({
    rule: rules[parseInt(props.parameters.ruleId, 10)],
    isFetching,
    hasFetchError
  })
}

const mapDispatchToProps: DispatchMapper<RuleDetailPageAttributes & InjectedIntlProps, DispatchAttributes> = (dispatch, props) => ({
  fetchRule: () => {
    dispatch(loadRuleDetail.request(parseInt(props.parameters.ruleId, 10)))
  },
  editRule: () => dispatch(push(interpolatePagePath('ruleEdit', props.parameters.ruleId))),
  deleteRule: (rule: NotificationRuleDetail) => (
    confirm(props.intl.formatMessage({ id: "cns.message.delete.confirm" })) && dispatch(deleteRemoteRule.request(parseInt(props.parameters.ruleId, 10)))
  )
})

export default injectIntl(
  connect(
    mapStateToProps,
    mapDispatchToProps)
    (RuleDetailPage)
)
