import * as React from 'react'
import { storiesOf } from "@storybook/react";

import '@/fleetdata/shared/styles/stylesheet.scss' 
import App from '@/fleetdata/App'
import { BMWButton as Button } from '@fleetdata/shared/components/button'
import Lock from '@fleetdata/shared/components/icons/lock.icon'
import FleetdataWrapper from './FleetdataWrapper';

storiesOf('Fleetdata - App', module)
  .add('Provided App', () =>  <App />)

storiesOf('Fleetdata - Components', module)
  .addDecorator(FleetdataWrapper)
  .add('Primary Button', () => 
  <Button 
    label="Prop: 'label'"
    primary="true"
    />)
    .add('Secondary Button', () => <Button label="Secondary Button" />)
    .add('Right Icon Button', () => <Button 
      label="RightIcon Button"
      primary="true"
      icon={<img src="/assets/svg/chevron-right.svg" />} />)
    .add('Left Icon Button', () => <Button 
      label="LeftIcon Button" 
      primary="true"
      iconleft={<Lock color="white" />} />)