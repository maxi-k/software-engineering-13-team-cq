import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import SingleComponentWrapper from '../SingleComponentWrapper'

/* ~~ General Components ~~ */
import RuleCreationStep, { RuleCreationStepEmbeddedProps, RuleCreationStepStandaloneProps }
  from '@/atoms/RuleCreationStep'
import RuleCreationStepper, { RuleCreationStepperProps }
  from '@/molecules/RuleCreationStepper'
import ClosingButton from '@/atoms/ClosingButton'

const creationStepProps: RuleCreationStepEmbeddedProps = {
  titleKey: "cns.rule.creation.step.general.title",
  completed: false
}

const creationStepStandaloneProps: RuleCreationStepStandaloneProps = {
  ...creationStepProps,
  active: true,
  selectStep: action('Select Step (Stanalone)')
}

const creationStepperProps: RuleCreationStepperProps = {
  activeStep: 1,
  selectStep: action('Select Step'),
  stepProps: [
    creationStepProps,
    {
      ...creationStepProps,
      titleKey: "cns.rule.creation.step.general.fleets"
    },
    {
      ...creationStepProps,
      titleKey: "cns.rule.creation.step.general.condition"
    },

  ]
}

storiesOf('Rule Creation / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Single Step', () => <RuleCreationStep {...creationStepStandaloneProps} />)
  .add('Stepper', () => <RuleCreationStepper {...creationStepperProps} style={{ width: '90%' }} />)

storiesOf('Rule Creation / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Close Procedure', () => <ClosingButton onClick={action('Closed procedure')} />)
  .add('Rule Tile', () => <RuleTile {...ruleTileProps} />)


/* ~~ Second-Step Components ~~ */
import FleetSelector, { FleetSelectorProps } from '@/atoms/FleetSelector'

const fleetSelectorProps: Partial<FleetSelectorProps> = {
  value: { label: 'BMW', value: 'BMW' },
  options: [
    { label: 'BMW', value: 'BMW' },
    { label: 'Audi', value: 'Audi' },
    { label: 'Mercedes', value: 'Mercedes' }
  ],
  onChange: action('Select'),
  styles: (_) => ({
    input: (base) => ({
      ...base,
      width: '500px'
    })
  })
}


storiesOf('Rule Creation / Second Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Fleet Selector', () => <FleetSelector {...fleetSelectorProps} />)
