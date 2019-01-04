import React from 'react'
import Stepper from '@material-ui/core/Stepper'
import RuleCreationStep, { RuleCreationStepEmbeddedProps } from '@/atoms/RuleCreationStep'

export interface RuleCreationStepperAttributes {
  activeStep: number,
  alternativeLabel?: boolean,
  stepProps: RuleCreationStepEmbeddedProps[],
  selectStep(stepIndex: number, event: React.SyntheticEvent<any, any>): void
}
export type RuleCreationStepperProps = RuleCreationStepperAttributes & React.HTMLAttributes<HTMLDivElement>

const RuleCreationStepper: React.SFC<RuleCreationStepperProps> =
  ({ stepProps, selectStep, ...stepperProps }) => (
    <Stepper {...stepperProps} >
      {stepProps.map((props, idx) => (
        <RuleCreationStep
          key={props.titleKey}
          active={idx === stepperProps.activeStep}
          selectStep={(event: React.SyntheticEvent<any, any>) => selectStep(idx, event)}
          {...props} />
      ))}
    </Stepper>
  )

export default RuleCreationStepper
