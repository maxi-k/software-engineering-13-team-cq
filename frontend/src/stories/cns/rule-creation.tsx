import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import StoryWrapper from '../StoryWrapper'
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
      titleKey: "cns.rule.creation.step.fleets.title"
    },
    {
      ...creationStepProps,
      titleKey: "cns.rule.creation.step.condition.title"
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

import RuleCreationGeneral from '@/organisms/RuleCreate/RuleCreationGeneral'
const updateFieldAction = (name: string | number) => action(`Update field ${name}`)
storiesOf('Rule Creation / First Step', module)
  .addDecorator(StoryWrapper)
  .add('Screen', () => <RuleCreationGeneral
    updateField={updateFieldAction}
    inProgressRule={{
      condition: {
        logicalConnective: LogicalConnective.Any,
        predicates: {}
      }
    }} />)

storiesOf('Rule Creation / Second Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Fleet Selector', () => <FleetSelector {...fleetSelectorProps} />)

/* ~~ Third-Step Components ~~ */
import PredicateCounter, { PredicateCounterProps } from '@/atoms/PredicateCounter'
import ConditionSelector, { ConditionSelectorProps } from '@/atoms/ConditionSelector'
import { LogicalConnective } from '@/model';

const predicateCounterProps: PredicateCounterProps = {
  value: { label: "all", value: "all" },
  options: [
    { label: "all", value: "all" },
    { label: "any", value: "any" },
    { label: "none", value: "none" }
  ],
  onChange: action('predicate counter'),
  beforeText: "cns.predicate.counter.beforetext",
  afterText: "cns.predicate.counter.aftertext"
}

const conditionSelectorProps: ConditionSelectorProps = {
  beforeText: "cns.condition.selector.beforetext",
  afterText: "cns.condition.selector.aftertext",
  onChangeDataType: action('change data type'),
  dataTypeValue: { label: "battery", value: "battery" },
  dataTypeOptions: [{
    label: "vehicle",
    options: [
      { label: "battery", value: "battery" },
      { label: "contract", value: "contract" },
      { label: "engine", value: "engine" },
      { label: "fuel", value: "fuel" },
      { label: "mileage", value: "mileage" },
      { label: "service", value: "service" },
    ]
  }],
  onChangeComparisonType: action('change comparison type'),
  comparisonTypeValue: { label: "equal", value: "equal" },
  comparisonTypeOptions: [
    { label: "equal", value: "equal" },
    { label: "unequal", value: "unequal" },
    { label: "above", value: "above" },
    { label: "below", value: "below" }
  ],
  onClickRemove: action('remove condition')
}

storiesOf('Rule Creation / Third Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Predicate Counter', () => <PredicateCounter {...predicateCounterProps} />)
  .add('Condition Selector', () => <ConditionSelector {...conditionSelectorProps} />)
