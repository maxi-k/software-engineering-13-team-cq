import React from 'react'
import { connect } from 'react-redux'
import { injectIntl, InjectedIntlProps } from 'react-intl'

import { StateMapper, DispatchMapper } from '@/state/connector'
import {
  editRuleAbort,
  editRuleSelectStep,
  editRulePreviousStep,
  finishRuleEditing,
  editRuleNextStep,
  editRuleUpdateField,
  editRuleSetErrors
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

const mapStateToProps: StateMapper<InjectedIntlProps, StateAttributes> = (state, props) => ({
  ...ruleEditingStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<InjectedIntlProps, DispatchAttributes> = (dispatch, props) => ({
  abortModification: () => (
    confirm(props.intl.formatMessage({ id: "cns.message.edit.abort.confirm" })) && dispatch(editRuleAbort())
  ),
  selectStep: (step: number) => dispatch(editRuleSelectStep(step)),
  previousStep: () => dispatch(editRulePreviousStep()),
  completeModification: (validationCallback: () => { [key: string]: string }) => {
    const errors = validationCallback()
    const errorMessages = Object.values(errors)
    dispatch(editRuleSetErrors(errors))
    if (errorMessages.length <= 0) {
      dispatch(finishRuleEditing.request())
    }
  },
  nextStep: (validationCallback: () => { [key: string]: string }) => {
    const errors = validationCallback()
    const errorMessages = Object.values(errors)
    dispatch(editRuleSetErrors(errors))
    if (errorMessages.length <= 0) {
      dispatch(editRuleNextStep())
    }
  },
  updateField: (name, callback) => (...values) => (
    dispatch(editRuleUpdateField(name, callback(...values)))
  )
})

export default injectIntl(
  connect(
    mapStateToProps,
    mapDispatchToProps)
    (RuleEditing)
)
