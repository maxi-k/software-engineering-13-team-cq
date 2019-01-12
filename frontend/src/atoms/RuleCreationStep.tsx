import React from 'react'
import { FormattedMessage } from 'react-intl'
import Step from '@material-ui/core/Step'
import StepLabel from '@material-ui/core/StepLabel'
import StepButton from '@material-ui/core/StepButton'

/**
 * Props that are required if this Step is
 * embedded in a stepper.
 */
export interface RuleCreationStepEmbeddedAttributes {
  titleKey: string,
  completed: boolean,
  disabled?: boolean,
  error?: boolean,
  optional?: boolean,
}
export type RuleCreationStepEmbeddedProps = RuleCreationStepEmbeddedAttributes & React.HTMLAttributes<HTMLDivElement>

/**
 * Props that are required if this Step is
 * not embedded in a stepper.
 * These are the props the component actually requires
 */
export interface RuleCreationStepStandaloneAttributes
  extends RuleCreationStepEmbeddedAttributes {
  active?: boolean,
  selectStep(event: React.SyntheticEvent<any, any>): void
}
export type RuleCreationStepStandaloneProps = RuleCreationStepStandaloneAttributes & React.HTMLAttributes<HTMLDivElement>

const RuleCreationStep: React.SFC<RuleCreationStepStandaloneProps> = ({
  titleKey, error, selectStep, ...stepProps
}) => (
    <Step {...stepProps} >
      <StepButton
        completed={stepProps.completed}
        onClick={selectStep}>
        <StepLabel error={error} disabled={stepProps.disabled} optional={stepProps.optional} >
          <FormattedMessage id={titleKey} />
        </StepLabel>
      </StepButton>
    </Step>
  )

export default RuleCreationStep
