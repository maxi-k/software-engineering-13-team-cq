import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import { NotificationRecipient, NotificationRecipientType } from '@/model/Rule'
import StoryWrapper from '../StoryWrapper'
import SingleComponentWrapper from '../SingleComponentWrapper'

/* ~~ General Components ~~ */
import ClosingButton from '@/modules/shared/components/ClosingButton'
import RuleRecipientTag from '@/modules/rule-detail/components/RuleRecipientTag'
import RuleModificationStep, { RuleModificationStepEmbeddedProps, RuleModificationStepStandaloneProps }
  from '@/modules/rule-modification/components/RuleModificationStep'
import RuleModificationStepper, { RuleModificationStepperProps }
  from '@/modules/rule-modification/components/RuleModificationStepper'

const creationStepProps: RuleModificationStepEmbeddedProps = {
  titleKey: "cns.rule.modification.step.general.title",
  completed: false
}

const creationStepStandaloneProps: RuleModificationStepStandaloneProps = {
  ...creationStepProps,
  active: true,
  selectStep: action('Select Step (Stanalone)')
}

const creationStepperProps: RuleModificationStepperProps = {
  activeStep: 1,
  selectStep: action('Select Step'),
  stepProps: [
    creationStepProps,
    {
      ...creationStepProps,
      titleKey: "cns.rule.modification.step.fleets.title"
    },
    {
      ...creationStepProps,
      titleKey: "cns.rule.modification.step.condition.title"
    },

  ]
}

const notificationRecipient: NotificationRecipient = {
  type: NotificationRecipientType.Email,
  value: ""
}

storiesOf('Rule Creation / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Single Step', () => <RuleModificationStep {...creationStepStandaloneProps} />)
  .add('Stepper', () => <RuleModificationStepper {...creationStepperProps} style={{ width: '90%' }} />)

storiesOf('Rule Creation / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Close Procedure', () => <ClosingButton onClick={action('Closed procedure')} />)

/* ~~ Second-Step Components ~~ */
import FleetSelector, { FleetSelectorProps } from '@/modules/rule-modification/components/FleetSelector'

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

import RuleModificationGeneral from '@/modules/rule-modification/parts/RuleModificationGeneral'
const updateFieldAction = (name: string | number) => action(`Update field ${name}`)
storiesOf('Rule Creation / First Step', module)
  .addDecorator(StoryWrapper)
  .add('Screen', () => <RuleModificationGeneral
    updateField={updateFieldAction}
    inProgressRule={{
      condition: {
        logicalConnective: LogicalConnective.Any,
        predicates: {}
      }
    }} />)
  .add('Rule Recipient Tag', () => <RuleRecipientTag recipient={notificationRecipient} />)

storiesOf('Rule Creation / Second Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Fleet Selector', () => <FleetSelector {...fleetSelectorProps} />)

/* ~~ Third-Step Components ~~ */
import PredicateCounter, { PredicateCounterProps } from '@/modules/rule-modification/components/PredicateCounter'
import ConditionSelector, { ConditionSelectorProps } from '@/modules/rule-modification/components/ConditionSelector'
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
  onChangeComparisonConstant: action('change comparison constant'),
  comparisonConstant: 'comparison constant',
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
