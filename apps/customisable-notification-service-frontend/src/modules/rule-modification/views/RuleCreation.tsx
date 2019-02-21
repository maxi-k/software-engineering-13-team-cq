import { connect } from 'react-redux'

import { StateMapper, DispatchMapper } from '@/state/connector'
import {
  createRuleAbort,
  createRuleSelectStep,
  createRulePreviousStep,
  finishRuleCreation,
  createRuleNextStep,
  createRuleUpdateField,
} from '@/state/rule'
import { ruleCreationStateSelector } from '@/state/selectors'
import RuleModification, { StateAttributes, DispatchAttributes } from './RuleModification'

const mapStateToProps: StateMapper<{}, StateAttributes> = (state, props) => ({
  ...ruleCreationStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<{}, DispatchAttributes> = (dispatch, props) => ({
  abortModification: () => (
    // TODO: This is a simple javascript dialog for now
    // implement as a modal that shows over the screen
    // See Issue #153
    confirm("Really abort rule creation?") && dispatch(createRuleAbort())
  ),
  selectStep: (step: number) => dispatch(createRuleSelectStep(step)),
  previousStep: () => dispatch(createRulePreviousStep()),
  completeModification: () => dispatch(finishRuleCreation.request()),
  nextStep: () => dispatch(createRuleNextStep()),
  updateField: (name, callback) => (...values) => (
    dispatch(createRuleUpdateField(name, callback(...values)))
  )
})

export default connect(
  mapStateToProps,
  mapDispatchToProps)
  (RuleModification)
