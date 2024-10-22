import React from 'react'
import { connect } from 'react-redux'
import { injectIntl, InjectedIntlProps } from 'react-intl'

import { StateMapper, DispatchMapper } from '@/state/connector'
import {
  createRuleAbort,
  createRuleSelectStep,
  createRulePreviousStep,
  finishRuleCreation,
  createRuleNextStep,
  createRuleUpdateField,
  createRuleSetErrors
} from '@/state/rule'
import { ruleCreationStateSelector } from '@/state/selectors'
import RuleModification, { StateAttributes, DispatchAttributes } from './RuleModification'

const RuleCreation: React.SFC<StateAttributes & DispatchAttributes> = (props) => (
  <RuleModification
    data-test-id="rule-creation-view"
    pageTitle="cns.page.ruleCreate.title"
    pageTitleProps={{ titleValues: { ruleName: props.inProgressRule.name } }}
    {...props}
  />
)

const mapStateToProps: StateMapper<InjectedIntlProps, StateAttributes> = (state, props) => ({
  ...ruleCreationStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<InjectedIntlProps, DispatchAttributes> = (dispatch, props) => ({
  abortModification: () => (
    // TODO: This is a simple javascript dialog for now
    // implement as a modal that shows over the screen
    // See Issue #153
    confirm(props.intl.formatMessage({ id: "cns.message.create.abort.confirm" })) && dispatch(createRuleAbort())
  ),
  selectStep: (step: number) => dispatch(createRuleSelectStep(step)),
  previousStep: () => dispatch(createRulePreviousStep()),
  completeModification: (validationCallback: () => { [key: string]: string }) => {
    const errors = validationCallback()
    const errorMessages = Object.values(errors)
    dispatch(createRuleSetErrors(errors))
    if (errorMessages.length <= 0) {
      dispatch(finishRuleCreation.request())
    }
  },
  nextStep: (validationCallback: () => { [key: string]: string }) => {
    const errors = validationCallback()
    const errorMessages = Object.values(errors)
    dispatch(createRuleSetErrors(errors))
    if (errorMessages.length <= 0) {
      dispatch(createRuleNextStep())
    }
  },
  updateField: (name, callback) => (...values) => (
    dispatch(createRuleUpdateField(name, callback(...values)))
  )
})

export default injectIntl(
  connect(
    mapStateToProps,
    mapDispatchToProps)
    (RuleCreation)
)
