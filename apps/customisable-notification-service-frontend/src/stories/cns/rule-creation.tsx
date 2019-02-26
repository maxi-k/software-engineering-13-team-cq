import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import { NotificationRecipient, NotificationRecipientType, NotificationRuleDetail } from '@/model/Rule'
import { ruleFieldValidator } from '@/services/rule-service'
import StoryWrapper from '../StoryWrapper'
import SingleComponentWrapper from '../SingleComponentWrapper'

/* ~~ General Components ~~ */
import ErrorMessage from '@/modules/shared/components/ErrorMessage'
import { FormattedMessage } from 'react-intl'
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

const invalidRule: Partial<NotificationRuleDetail> = {
  ruleId: undefined, name: undefined, description: undefined, aggregator: undefined,
  recipients: undefined, owner: undefined, ownerAsAdditionalRecipient: undefined,
  fleets: undefined, applyToAllFleets: undefined, condition: undefined
}

storiesOf('Rule Creation / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Single Step', () => <RuleModificationStep {...creationStepStandaloneProps} />)
  .add('Stepper', () => <RuleModificationStepper {...creationStepperProps} style={{ width: '90%' }} />)
  .add('Validator Messages', () => (
    <>
      {Object.values(ruleFieldValidator.validateAllFields(invalidRule)).map((validationMessage) => (
        <div style={{ display: 'block', padding: '1rem' }}>
          <ErrorMessage message={
            <FormattedMessage id={validationMessage} />
          } />
        </div>
      ))
      }
    </>
  ))

storiesOf('Rule Creation / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Close Procedure', () => <ClosingButton onClick={action('Closed procedure')} />)

import RecipientSelector, { RecipientSelectorProps } from '@/modules/rule-modification/components/RecipientSelector'

const recipientSelectorProps: Partial<RecipientSelectorProps> = {
  value: { label: 'Example', value: 'Example' },
  onChange: action('Select Recipient'),
  styles: (_) => ({
    input: (base) => ({
      ...base,
      width: '500px'
    })
  })
}

import { initialModificationState } from '@/state/rule/common'
import RuleModificationGeneral from '@/modules/rule-modification/parts/RuleModificationGeneral'

const fleetSelectorProps: Partial<FleetSelectorProps> = {
  value: { label: 'BMW', value: 'BMW' },
  options: [
    { label: 'BMW', value: 'BMW' },
    { label: 'Audi', value: 'Audi' },
    { label: 'Mercedes', value: 'Mercedes' }
  ],
  onChange: action('Select Fleet'),
  styles: (_) => ({
    input: (base) => ({
      ...base,
      width: '500px'
    })
  })
}

const inProgressRule = initialModificationState.inProgressRule

const updateFieldAction = (name: string | number) => action(`Update field ${name}`)
storiesOf('Rule Creation / First Step', module)
  .addDecorator(StoryWrapper)
  .add('Screen', () => <RuleModificationGeneral
    updateField={updateFieldAction}
    inProgressRule={inProgressRule} />)
  .add('Rule Recipient Tag', () => <RuleRecipientTag recipient={notificationRecipient} />)
  .add('Recipient Selector', () => <RecipientSelector {...recipientSelectorProps} />)

/* ~~ Second-Step Components ~~ */
import RuleModificationFleets from '@/modules/rule-modification/parts/RuleModificationFleets'
import FleetSelector, { FleetSelectorProps } from '@/modules/rule-modification/components/FleetSelector'

storiesOf('Rule Creation / Second Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Fleet Selector', () => <FleetSelector {...fleetSelectorProps} />)

storiesOf('Rule Creation / Second Step', module)
  .addDecorator(StoryWrapper)
  .add('Screen', () => <RuleModificationFleets
    updateField={updateFieldAction}
    inProgressRule={inProgressRule}
  />)
/* ~~ Third-Step Components ~~ */
import RuleModificationCondition from '@/modules/rule-modification/parts/RuleModificationCondition'
import PredicateCounter, { PredicateCounterProps } from '@/modules/rule-modification/components/PredicateCounter'
import ConditionSelector, { ConditionSelectorProps } from '@/modules/rule-modification/components/ConditionSelector'

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
  unit: "text unit",
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

storiesOf('Rule Creation / Third Step', module)
  .addDecorator(StoryWrapper)
  .add('Screen', () => <RuleModificationCondition
    updateField={updateFieldAction}
    inProgressRule={inProgressRule}
  />)

/* ~~ Fourth-Step Components ~~ */
import { AggregatorStrategy } from '@/model'
import RuleModificationAggregator from '@/modules/rule-modification/parts/RuleModificationAggregator'
import AggregatorSelector, { AggregatorSelectorProps } from '@/modules/rule-modification/components/AggregatorSelector'
import ScheduledAggregator from '@/modules/rule-modification/components/ScheduledAggregator'
import CountingAggregator from '@/modules/rule-modification/components/CountingAggregator'
import { AggregatorComponentAttributes } from '@/modules/rule-modification/aggregator-common';

const aggregatorComponentProps: AggregatorComponentAttributes = {
  aggregator: {
    strategy: AggregatorStrategy.Immediate,
    value: ''
  },
  setAggregatorValue: (value: any) => action('Setting AggregatorValue')
}

const aggregatorSelectorProps: AggregatorSelectorProps = {
  ...aggregatorComponentProps,
  setAggregatorStrategy: (strategy: any) => action('Setting AggregatorStrategy'),
}

storiesOf('Rule Creation / Fourth Step', module)
  .addDecorator(SingleComponentWrapper)
  .add('Counting Aggregator', () => <CountingAggregator {...aggregatorComponentProps} />)
  .add('Scheduled Aggregator', () => <ScheduledAggregator {...aggregatorComponentProps} />)
  .add('Aggregator Selector', () => <AggregatorSelector {...aggregatorSelectorProps} />)

storiesOf('Rule Creation / Fourth Step', module)
  .addDecorator(StoryWrapper)
  .add('Screen', () => <RuleModificationAggregator
    updateField={updateFieldAction}
    inProgressRule={inProgressRule}
  />)
