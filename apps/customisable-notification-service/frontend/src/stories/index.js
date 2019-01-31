import React from 'react';

import { storiesOf } from '@storybook/react';
// import { action } from '@storybook/addon-actions';
// import { linkTo } from '@storybook/addon-links';
import Welcome from './Welcome'

storiesOf('Welcome', module)
  .add('to this Storybook', () => <Welcome />);

require('./fleetdata/index')
require('./cns/index')
