import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import SingleComponentWrapper from '../SingleComponentWrapper'
import { PredicateCounterValue, ComparisonType, VehicleDataType } from '@/model/Rule'

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

/* ~~ Third-Step Components ~~ */
import PredicateCounter, { PredicateCounterProps } from '@/atoms/PredicateCounter'
import ConditionSelector, { ConditionSelectorProps } from '@/atoms/ConditionSelector'

const predicateCounterProps: PredicateCounterProps = {
  options: [ PredicateCounterValue.All, PredicateCounterValue.Any, PredicateCounterValue.None ]
}

const conditionSelectorProps: ConditionSelectorProps = {
  dataTypes: [VehicleDataType.Battery, VehicleDataType.Contract, VehicleDataType.Engine, VehicleDataType.Service, VehicleDataType.Fuel, VehicleDataType.Mileage],
  comparisonTypes: [ComparisonType.Above, ComparisonType.Below, ComparisonType.EqualTo, ComparisonType.UnequalTo],
}

storiesOf('Rule Creation / Third Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Predicate Counter', () => <PredicateCounter {...predicateCounterProps} />)
  .add('Condition Selector', () => <ConditionSelector {...conditionSelectorProps} />)
