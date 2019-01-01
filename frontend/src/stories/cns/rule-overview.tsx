import React from 'react'
import { storiesOf } from '@storybook/react'
import SingleComponentWrapper from '../SingleComponentWrapper'

import AddRuleTile from '@/atoms/AddRuleTile'
import RuleTile, { RuleTileProps } from '@/atoms/RuleTile'
import { VehicleDataType } from '@/model/Rule'

const ruleTileProps: RuleTileProps = {
  rule: {
    name: 'Rule Name',
    description: 'Rule Description for an examplary Rule',
    aggregatorDescription: 'Sent every Tuesday, 9:00 AM',
    dataSources: [
      VehicleDataType.Engine,
      VehicleDataType.Battery
    ]
  }
}

storiesOf('Rule Overview', module)
  .addDecorator(SingleComponentWrapper)
  .add('Add Rule Tile', AddRuleTile)
  .add('Rule Tile', () => <RuleTile {...ruleTileProps} />)
