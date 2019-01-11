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
  options: [ PredicateCounterValue.All, PredicateCounterValue.Any, PredicateCounterValue.None ],
  beforeText: "cns.predicate.counter.beforetext",
  afterText: "cns.predicate.counter.aftertext"
}

const conditionSelectorProps: ConditionSelectorProps = {
  dataTypes: [{ label: VehicleDataType.Battery, value: VehicleDataType.Battery },
  { label: VehicleDataType.Contract, value: 'cns.vehicle.status.'+VehicleDataType.Battery+'.label' },
  { label: VehicleDataType.Engine, value: VehicleDataType.Engine },
  { label: VehicleDataType.Fuel, value: VehicleDataType.Fuel },
  { label: VehicleDataType.Mileage, value: VehicleDataType.Mileage },
  { label: VehicleDataType.Service, value: VehicleDataType.Service }],
  comparisonTypes: [ComparisonType.Above, ComparisonType.Below, ComparisonType.EqualTo, ComparisonType.UnequalTo],
}

storiesOf('Rule Creation / Third Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Predicate Counter', () => <PredicateCounter {...predicateCounterProps} />)
  .add('Condition Selector', () => <ConditionSelector {...conditionSelectorProps} />)
