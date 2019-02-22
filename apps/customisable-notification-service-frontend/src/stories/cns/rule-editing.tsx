import React from 'react'
import { storiesOf } from '@storybook/react'

import StoryWrapper from '../StoryWrapper'

import RuleEditView from '@/modules/rule-modification/views/RuleEditing'

storiesOf('Rule Editing / Complete', module)
  .addDecorator(StoryWrapper)
  .add('Edit existing Rule', () => <RuleEditView />)
