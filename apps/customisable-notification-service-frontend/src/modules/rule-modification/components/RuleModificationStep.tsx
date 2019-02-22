import React from 'react'
import { FormattedMessage } from 'react-intl'
import Step from '@material-ui/core/Step'
import StepLabel from '@material-ui/core/StepLabel'
import StepButton from '@material-ui/core/StepButton'

/**
 * Props that are required if this Step is
 * embedded in a stepper.
 */
export interface RuleModificationStepEmbeddedAttributes {
  titleKey: string,
  completed: boolean,
  disabled?: boolean,
  error?: boolean,
  optional?: boolean,
}
export type RuleModificationStepEmbeddedProps = RuleModificationStepEmbeddedAttributes
                                              & React.HTMLAttributes<HTMLDivElement>

/**
 * Props that are required if this Step is
 * not embedded in a stepper.
 * These are the props the component actually requires
 */
export interface RuleModificationStepStandaloneAttributes
  extends RuleModificationStepEmbeddedAttributes {
  active?: boolean,
  selectStep(event: React.SyntheticEvent<any, any>): void
}
export type RuleModificationStepStandaloneProps = RuleModificationStepStandaloneAttributes
                                                & React.HTMLAttributes<HTMLDivElement>

const RuleModificationStep: React.SFC<RuleModificationStepStandaloneProps> = ({
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

export default RuleModificationStep
