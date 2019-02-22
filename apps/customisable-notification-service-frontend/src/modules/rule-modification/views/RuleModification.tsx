import React, { lazy, Suspense } from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'
import { RuleModificationState } from '@/state/rule'

import Typography from '@material-ui/core/Typography'
import { BMWButton as Button } from '@fleetdata/shared/components/button'
import NextIcon from '@fleetdata/shared/components/icons/chevron-right.icon'

import ErrorMessage from '@/modules/shared/components/ErrorMessage'
import LoadingIndicator from '@/modules/shared/components/LoadingIndicator'
import ViewHeader from '@/modules/shared/components/ViewHeader'
import AbortButton from '@/modules/shared/components/BackButton'

import RuleModificationStepper from '../components/RuleModificationStepper'

import {
  StyledRuleModificationWrapper,
  CommonRuleModificationStateAttributes,
  CommonRuleModificationDispatchAttributes,
  RuleModificationStepView,
  RuleModificationStepViewProps
} from '../modification-common'

export type StateAttributes = CommonRuleModificationStateAttributes & RuleModificationState

type SelectStepType = (step: number) => void
export interface DispatchAttributes
  extends CommonRuleModificationDispatchAttributes {
  selectStep: SelectStepType,
  completeModification(): void,
  nextStep(): void,
  previousStep(): void,
  abortModification(): void
}

export type RuleModificationProps = StateAttributes
  & DispatchAttributes
  & React.HTMLAttributes<HTMLDivElement>

const stepTitleKeys = ["general", "fleets", "condition", "timing", "summary"]

const stepComponents: Array<React.LazyExoticComponent<RuleModificationStepView>> = [
  lazy(() => import('../parts/RuleModificationGeneral')),
  lazy(() => import('../parts/RuleModificationFleets')),
  lazy(() => import('../parts/RuleModificationCondition')),
  lazy(() => import('../parts/RuleModificationAggregator')),
  lazy(() => import('../parts/RuleModificationSummary'))
]

const stepperProps = (activeStep: number, selectStep: SelectStepType, completedSteps: Set<number>) => ({
  activeStep,
  selectStep: (stepIndex: number, _: React.SyntheticEvent<any, any>) => selectStep(stepIndex),
  stepProps: stepTitleKeys.map((titleKey, index) => ({
    titleKey: `cns.rule.modification.step.${titleKey}.title`,
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
  stepProps: RuleModificationStepViewProps
}
const CurrentStepView: React.SFC<CurrentStepViewProps> = (
  { currentStep, stepProps }) => {
  if (currentStep < 0 || currentStep >= stepComponents.length) {
    return <ErrorMessage message={<FormattedMessage id="cns.rule.modification.error.default" />} />
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
      <FormattedMessage id="cns.rule.modification.step.label" />
      {' '}{currentStep + 1}{': '}
      <FormattedMessage id={`cns.rule.modification.step.${stepTitleKeys[currentStep]}.title`} />
    </Typography>
  </StyledStepTitle>
)

const RuleModification: React.SFC<RuleModificationProps> = (
  { abortModification, selectStep, nextStep, previousStep,
    completeModification, inProgressRule, updateField,
    completedSteps, currentStep,
    ...props }
) => (
    <StyledRuleModificationWrapper {...props}>
      <ViewHeader title="cns.page.ruleCreate.title">
        <AbortButton
          label="cns.navigation.abort.label"
          onClick={abortModification} />
      </ViewHeader>
      <RuleModificationStepper
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
            <FormattedMessage id="cns.rule.modification.action.step.previous" />
          </Button>
        }
        {currentStep === stepComponents.length - 1
          ?
          <Button primary="true"
            icon={<NextIcon fill="#fff" />}
            onClick={completeModification}>
            <FormattedMessage id="cns.rule.modification.action.complete" />
          </Button>
          :
          <Button primary="true"
            icon={<NextIcon fill="#fff" />}
            onClick={nextStep}>
            <FormattedMessage id="cns.rule.modification.action.step.next" />
          </Button>
        }
      </StyledActionArea>
    </StyledRuleModificationWrapper>
  )

export default RuleModification
