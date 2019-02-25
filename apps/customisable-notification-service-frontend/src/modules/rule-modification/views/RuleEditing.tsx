import React from 'react'
import { connect } from 'react-redux'

import { StateMapper, DispatchMapper } from '@/state/connector'
import {
  editRuleAbort,
  editRuleSelectStep,
  editRulePreviousStep,
  finishRuleEditing,
  editRuleNextStep,
  editRuleUpdateField,
} from '@/state/rule'
import { ruleEditingStateSelector } from '@/state/selectors'
import RuleModification, { StateAttributes, DispatchAttributes } from './RuleModification'

const RuleEditing: React.SFC<StateAttributes & DispatchAttributes> = (props) => (
  <RuleModification
    pageTitle="cns.page.ruleEdit.title"
    pageTitleProps={{ titleValues: { ruleName: props.inProgressRule.name } }}
    nonLinearStepper={true}
    {...props} />
)

const mapStateToProps: StateMapper<{}, StateAttributes> = (state, props) => ({
  ...ruleEditingStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<{}, DispatchAttributes> = (dispatch, props) => ({
  abortModification: () => (
    // TODO: This is a simple javascript dialog for now
    // implement as a modal that shows over the screen
    // See Issue #153
    confirm("Really abort rule editing?") && dispatch(editRuleAbort())
  ),
  selectStep: (step: number) => dispatch(editRuleSelectStep(step)),
  previousStep: () => dispatch(editRulePreviousStep()),
  completeModification: () => dispatch(finishRuleEditing.request()),
  nextStep: () => dispatch(editRuleNextStep()),
  updateField: (name, callback) => (...values) => (
    dispatch(editRuleUpdateField(name, callback(...values)))
  )
})

export default connect(
  mapStateToProps,
  mapDispatchToProps)
  (RuleEditing)
