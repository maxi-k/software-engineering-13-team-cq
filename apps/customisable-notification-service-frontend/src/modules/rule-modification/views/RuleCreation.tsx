import React, { lazy, Suspense } from 'react'
import styled from 'styled-components'
import { connect } from 'react-redux'
import { FormattedMessage } from 'react-intl'

import { StateMapper, DispatchMapper } from '@/state/connector'
import {
  createRuleAbort,
  createRuleSelectStep,
  createRulePreviousStep,
  finishRuleCreation,
  createRuleNextStep,
  createRuleUpdateField,
  RuleCreationState
} from '@/state/rule'
import { ruleCreationStateSelector } from '@/state/selectors'

import Typography from '@material-ui/core/Typography'
import { BMWButton as Button } from '@fleetdata/shared/components/button'
import NextIcon from '@fleetdata/shared/components/icons/chevron-right.icon'

import ErrorMessage from '@/modules/shared/components/ErrorMessage'
import LoadingIndicator from '@/modules/shared/components/LoadingIndicator'
import ViewHeader from '@/modules/shared/components/ViewHeader'
import AbortButton from '@/modules/shared/components/BackButton'

import RuleCreationStepper from '../components/RuleCreationStepper'

import {
  StyledRuleCreationWrapper,
  CommonRuleCreationStateAttributes,
  CommonRuleCreationDispatchAttributes,
  RuleCreationStepView,
  RuleCreationStepViewProps
} from '../creation-common'

type StateAttributes = CommonRuleCreationStateAttributes & RuleCreationState

type SelectStepType = (step: number) => void
interface DispatchAttributes
  extends CommonRuleCreationDispatchAttributes {
  selectStep: SelectStepType,
  completeCreation(): void,
  nextStep(): void,
  previousStep(): void,
  abortCreation(): void
}

type RuleCreateProps = StateAttributes & DispatchAttributes & React.HTMLAttributes<HTMLDivElement>

const stepTitleKeys = ["general", "fleets", "condition", "timing", "summary"]

const stepComponents: Array<React.LazyExoticComponent<RuleCreationStepView>> = [
  lazy(() => import('../parts/RuleCreationGeneral')),
  lazy(() => import('../parts/RuleCreationFleets')),
  lazy(() => import('../parts/RuleCreationCondition')),
  lazy(() => import('../parts/RuleCreationAggregator')),
  lazy(() => import('../parts/RuleCreationSummary'))
]

const stepperProps = (activeStep: number, selectStep: SelectStepType, completedSteps: Set<number>) => ({
  activeStep,
  selectStep: (stepIndex: number, _: React.SyntheticEvent<any, any>) => selectStep(stepIndex),
  stepProps: stepTitleKeys.map((titleKey, index) => ({
    titleKey: `cns.rule.creation.step.${titleKey}.title`,
    completed: completedSteps.has(index),
  }))
})

const StyledActionArea = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    padding-top: 1.5rem;

    ${Button} {
        margin: 0 0 0 1rem;
    }

`

interface CurrentStepViewProps {
  currentStep: number,
  stepProps: RuleCreationStepViewProps
}
const CurrentStepView: React.SFC<CurrentStepViewProps> = (
  { currentStep, stepProps }) => {
  if (currentStep < 0 || currentStep >= stepComponents.length) {
    return <ErrorMessage message={<FormattedMessage id="cns.rule.creation.error.default" />} />
  }
  const Component = stepComponents[currentStep]
  return (
    <Suspense fallback={<LoadingIndicator isCentral={true} />}>
      <Component {...stepProps} />
    </Suspense>
  )
}

const StyledStepTitle = styled.div`
  padding: 0.5rem 0 1rem 0;
`

const CurrentStepTitle: React.SFC<{ currentStep: number }> = ({ currentStep }) => (
  <StyledStepTitle>
    <Typography variant="h6">
      <FormattedMessage id="cns.rule.creation.step.label" />
      {' '}{currentStep + 1}{': '}
      <FormattedMessage id={`cns.rule.creation.step.${stepTitleKeys[currentStep]}.title`} />
    </Typography>
  </StyledStepTitle>
)

const RuleCreate: React.SFC<RuleCreateProps> = (
  { abortCreation, selectStep, nextStep, previousStep,
    completeCreation, inProgressRule, updateField,
    completedSteps, currentStep,
    ...props }
) => (
    <StyledRuleCreationWrapper {...props}>
      <ViewHeader title="cns.page.ruleCreate.title">
        <AbortButton
          label="cns.navigation.abort.label"
          onClick={abortCreation} />
      </ViewHeader>
      <RuleCreationStepper
        {...stepperProps(currentStep, selectStep, completedSteps)}
      />
      <CurrentStepTitle currentStep={currentStep} />
      <CurrentStepView
        currentStep={currentStep}
        stepProps={{
          updateField,
          inProgressRule
        }} />
      <StyledActionArea>
        {currentStep !== 0 &&
          <Button primary="false"
            onClick={previousStep}>
            <FormattedMessage id="cns.rule.creation.action.step.previous" />
          </Button>
        }
        {currentStep === stepComponents.length - 1
          ?
          <Button primary="true"
            icon={<NextIcon fill="#fff" />}
            onClick={completeCreation}>
            <FormattedMessage id="cns.rule.creation.action.complete" />
          </Button>
          :
          <Button primary="true"
            icon={<NextIcon fill="#fff" />}
            onClick={nextStep}>
            <FormattedMessage id="cns.rule.creation.action.step.next" />
          </Button>
        }
      </StyledActionArea>
    </StyledRuleCreationWrapper>
  )

const mapStateToProps: StateMapper<{}, StateAttributes> = (state, props) => ({
  ...ruleCreationStateSelector(state)
})

const mapDispatchToProps: DispatchMapper<{}, DispatchAttributes> = (dispatch, props) => ({
  abortCreation: () => (
    // TODO: This is a simple javascript dialog for now
    // implement as a modal that shows over the screen
    confirm("Really abort rule creation?") && dispatch(createRuleAbort())
  ),
  selectStep: (step: number) => dispatch(createRuleSelectStep(step)),
  previousStep: () => dispatch(createRulePreviousStep()),
  completeCreation: () => dispatch(finishRuleCreation.request()),
  nextStep: () => dispatch(createRuleNextStep()),
  updateField: (name, callback) => (...values) => (
    dispatch(createRuleUpdateField(name, callback(...values)))
  )
})

export default connect(
  mapStateToProps,
  mapDispatchToProps)
  (RuleCreate)
