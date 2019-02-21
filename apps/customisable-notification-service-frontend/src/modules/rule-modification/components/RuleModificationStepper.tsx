import React from 'react'
import Stepper from '@material-ui/core/Stepper'
import RuleModificationStep, { RuleModificationStepEmbeddedProps } from './RuleModificationStep'

export type SelectStepType = (stepIndex: number, event: React.SyntheticEvent<any, any>) => void
export interface RuleModificationStepperAttributes {
  activeStep: number,
  alternativeLabel?: boolean,
  stepProps: RuleModificationStepEmbeddedProps[],
  selectStep: SelectStepType
}
export type RuleModificationStepperProps = RuleModificationStepperAttributes
                                         & React.HTMLAttributes<HTMLDivElement>

const stepSelector = (selectStep: SelectStepType, stepIndex: number) =>
  (event: React.SyntheticEvent<any, any>): void =>
    selectStep(stepIndex, event)

const RuleModificationStepper: React.SFC<RuleModificationStepperProps> =
  ({ stepProps, selectStep, ...stepperProps }) => (
    <Stepper {...stepperProps} >
      {stepProps.map((props, idx) => (
        <RuleModificationStep
          key={props.titleKey}
          active={idx === stepperProps.activeStep}
          selectStep={stepSelector(selectStep, idx)}
          {...props} />
      ))}
    </Stepper>
  )

export default RuleModificationStepper
