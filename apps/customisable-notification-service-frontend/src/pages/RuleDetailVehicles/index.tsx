import React from 'react'
import styled from 'styled-components'
import { push } from 'connected-react-router'
import { connect } from 'react-redux'
import { StateMapper, DispatchMapper } from '@/state/connector'
import { ruleDetailStateSelector } from '@/state/selectors'
import { loadRuleDetail } from '@/state/rule'
import { interpolatePagePath } from '@/pages/page-definitions'
import { FetchingAttributes, NotificationRuleDetail } from '@/model'
import RuleDetailVehicles, { FinishVehiclesType, AbortVehiclesType } from '@/modules/rule-detail/parts/RuleDetailVehicles'

export interface RuleDetailVehiclesPageAttributes {
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
  finishVehicles: FinishVehiclesType,
  abortVehicles: AbortVehiclesType,
  fetchRule(): void,
  editRule(): void,
  deleteRule(): void
}

export type RuleDetailVehiclePageProps = RuleDetailVehiclesPageAttributes
  & StateAttributes
  & DispatchAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledPageWrapper = styled.div`
    flex-grow: 1;
`

class RuleDetailVehiclePage extends React.PureComponent<RuleDetailVehiclePageProps> {

  public componentDidMount = () => {
    const {fetchRule} = this.props
    fetchRule()
  }

  public render = () => {
    const {parameters, fetchRule, editRule, deleteRule, ...ruleDetailProps} = this.props
    return (
      <StyledPageWrapper>
        <RuleDetailVehicles {...ruleDetailProps}/>
      </StyledPageWrapper>
    )
  }
}

const mapStateToProps: StateMapper<RuleDetailVehiclesPageAttributes, StateAttributes> = (state, props) => {
  const {rules, isFetching, hasFetchError} = ruleDetailStateSelector(state)
  return ({
    rule: rules[parseInt(props.parameters.ruleId, 10)],
    isFetching,
    hasFetchError
  })
}

const mapDispatchToProps: DispatchMapper<RuleDetailVehiclesPageAttributes, DispatchAttributes> = (dispatch, props) => ({
  finishVehicles: (event) => dispatch(push(interpolatePagePath('ruleDetailCondition'))),
  abortVehicles: (event) => dispatch(push(interpolatePagePath('ruleOverview'))),
  fetchRule: () => {
    dispatch(loadRuleDetail.request(parseInt(props.parameters.ruleId, 10)))
  },
  editRule: () => dispatch(push(interpolatePagePath('ruleEdit', props.parameters.ruleId))),
  deleteRule: () => alert('deleting rule')
})

export default connect(mapStateToProps, mapDispatchToProps)(RuleDetailVehiclePage)
