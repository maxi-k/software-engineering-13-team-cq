import React from 'react'
import styled from 'styled-components'

import { connect, StateMapper, DispatchMapper } from '@/state/connector'
import { ruleEditingStateSelector } from '@/state/selectors'
import { loadEditingRuleInitial, RuleModificationState } from '@/state/rule'

import RuleEdit from '@/modules/rule-modification/views/RuleEditing'

export interface RuleEditPageAttributes {
  parameters: {
    ruleId: string // needs to be a string because it's passed down by router
  }
}

interface RuleEditPageDispatchAttributes {
  fetchEditableRule(): void
}

export type RuleEditPageProps =
  RuleModificationState
  & RuleEditPageAttributes
  & RuleEditPageDispatchAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleEditPage = styled.div`
`

class RuleEditPage extends React.Component<RuleEditPageProps> {

  public componentDidMount = () => {
    const { fetchEditableRule } = this.props
    fetchEditableRule()
  }

  public render = () => {
    return (
      <StyledRuleEditPage>
        <RuleEdit />
      </StyledRuleEditPage >
    )
  }
}

const mapStateToProps: StateMapper<RuleEditPageAttributes, RuleModificationState> = (state, ownProps) => ({
  ...ruleEditingStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<RuleEditPageAttributes, RuleEditPageDispatchAttributes> = (dispatch, props) => ({
  fetchEditableRule: () => dispatch(loadEditingRuleInitial.request(parseInt(props.parameters.ruleId, 10))),
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RuleEditPage)
