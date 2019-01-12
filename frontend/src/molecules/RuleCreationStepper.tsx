import React from 'react'
import Stepper from '@material-ui/core/Stepper'
import RuleCreationStep, { RuleCreationStepEmbeddedProps } from '@/atoms/RuleCreationStep'

export type SelectStepType = (stepIndex: number, event: React.SyntheticEvent<any, any>) => void
export interface RuleCreationStepperAttributes {
  activeStep: number,
  alternativeLabel?: boolean,
  stepProps: RuleCreationStepEmbeddedProps[],
  selectStep: SelectStepType
}
export type RuleCreationStepperProps = RuleCreationStepperAttributes & React.HTMLAttributes<HTMLDivElement>

const stepSelector = (selectStep: SelectStepType, stepIndex: number) =>
  (event: React.SyntheticEvent<any, any>): void =>
    selectStep(stepIndex, event)

const RuleCreationStepper: React.SFC<RuleCreationStepperProps> =
  ({ stepProps, selectStep, ...stepperProps }) => (
    <Stepper {...stepperProps} >
      {stepProps.map((props, idx) => (
        <RuleCreationStep
          key={props.titleKey}
          active={idx === stepperProps.activeStep}
          selectStep={stepSelector(selectStep, idx)}
          {...props} />
      ))}
    </Stepper>
  )

export default RuleCreationStepper
