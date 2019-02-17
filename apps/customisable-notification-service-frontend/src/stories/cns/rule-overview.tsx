import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import StoryWrapper from '../StoryWrapper'
import SingleComponentWrapper from '../SingleComponentWrapper'

import AddRuleTile from '@/modules/rule-overview/components/AddRuleTile'
import RuleTile, { RuleTileProps } from '@/modules/rule-overview/components/RuleTile'
import RuleOverview, { RuleOverviewProps } from '@/modules/rule-overview/views/RuleOverview'
import { VehicleDataType } from '@/model'

const ruleTileProps: RuleTileProps = {
  rule: {
    ruleId: 1,
    name: 'Rule Name',
    description: 'Rule Description for an examplary Rule',
    aggregatorDescription: 'Sent every Tuesday, 9:00 AM',
    dataSources: [
      VehicleDataType.Engine,
      VehicleDataType.Battery
    ]
  }
}

const ruleOverviewProps: RuleOverviewProps = {
  rules: [ruleTileProps.rule, { ...ruleTileProps.rule, ruleId: 2, name: 'Rule Name 2' }],
  addRule: action('add rule'),
  selectRule: action('select rule'),
  isFetching: false,
  hasFetchError: false
}

storiesOf('Rule Overview / Tiles', module)
  .addDecorator(SingleComponentWrapper)
  .add('Add Rule Tile', () => <AddRuleTile onClick={action('Add New Rule!')} />)
  .add('Rule Tile', () => <RuleTile {...ruleTileProps} />)

storiesOf('Rule Overview / Complete', module)
  .addDecorator(StoryWrapper)
  .add('Successful', () => <RuleOverview {...ruleOverviewProps} />)
  .add('Fetching', () => <RuleOverview {...{ ...ruleOverviewProps, isFetching: true }} />)
  .add('With Error', () => <RuleOverview {...{ ...ruleOverviewProps, hasFetchError: true }} />)
