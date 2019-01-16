import React, { lazy, Suspense } from 'react'
import styled from 'styled-components'
import { connect } from 'react-redux'
import { FormattedMessage } from 'react-intl'

import { StateMapper, DispatchMapper } from '@/state/connector'
import {
  createRuleAbort,
  createRuleSelectStep,
  createRuleUpdateField,
  RuleCreationState
} from '@/state/rule'
import { ruleCreationStateSelector } from '@/state/selectors'

import Typography from '@material-ui/core/Typography'
import { BMWButton as Button } from '@fleetdata/shared/components/button'
import BackIcon from '@fleetdata/shared/components/icons/chevron-left.icon'
import NextIcon from '@fleetdata/shared/components/icons/chevron-right.icon'

import ErrorMessage from '@/atoms/ErrorMessage'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import ViewHeader from '@/molecules/ViewHeader'
import ClosingButton from '@/atoms/ClosingButton'
import RuleCreationStepper from '@/molecules/RuleCreationStepper'

import {
  StyledRuleCreationWrapper,
  CommonRuleCreationStateAttributes,
  CommonRuleCreationDispatchAttributes,
  RuleCreationStepView,
  RuleCreationStepViewProps
} from './common'

type StateAttributes = CommonRuleCreationStateAttributes & RuleCreationState

type SelectStepType = (step: number) => void
interface DispatchAttributes
  extends CommonRuleCreationDispatchAttributes {
  selectStep: SelectStepType,
  abortCreation(): void
}

type RuleCreateProps = StateAttributes & DispatchAttributes & React.HTMLAttributes<HTMLDivElement>

const stepTitleKeys = ["general", "fleets", "condition", "timing", "summary"]

const stepperProps = (activeStep: number, selectStep: SelectStepType, completedSteps: Set<number>) => ({
  activeStep,
  selectStep: (stepIndex: number, _: React.SyntheticEvent<any, any>) => selectStep(stepIndex),
  stepProps: stepTitleKeys.map((titleKey, index) => ({
    titleKey: `cns.rule.creation.step.${titleKey}.title`,
    completed: completedSteps.has(index),
  }))
})

const stepComponents: React.LazyExoticComponent<RuleCreationStepView>[] = [
  lazy(() => import('./RuleCreationGeneral'))
]

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
  { abortCreation, selectStep, updateField,
    completedSteps, currentStep, inProgressRule,
    ...props }
) => (
    <StyledRuleCreationWrapper {...props}>
      <ViewHeader title="cns.page.ruleCreate.title">
        <ClosingButton
          onClick={abortCreation}
          width={40} height={40} />
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
          <Button primary="false" iconleft={<BackIcon fill="#fff" />} >
            <FormattedMessage id="cns.rule.creation.action.step.previous" />
          </Button>
        }
        {currentStep === stepComponents.length - 1
          ?
          <Button primary="true" icon={<NextIcon fill="#fff" />} >
            <FormattedMessage id="cns.rule.creation.action.complete" />
          </Button>
          :
          <Button primary="true" icon={<NextIcon fill="#fff" />} >
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
  updateField: (name, callback) => (value) => (
    dispatch(createRuleUpdateField(name, callback(value)))
  )
})

export default connect(
  mapStateToProps,
  mapDispatchToProps)
  (RuleCreate)
