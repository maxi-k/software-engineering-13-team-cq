import React from 'react'
import { storiesOf } from '@storybook/react'
import StoryWrapper from '../StoryWrapper'

import RuleOverviewPage from '@/pages/RuleOverview'
import RuleDetailPage from '@/pages/RuleDetail'
import RuleCreationPage from '@/pages/RuleCreate'
import RuleEditingPage from '@/pages/RuleEdit'

storiesOf('Complete Pages', module)
  .addDecorator(StoryWrapper)
  .add('Rule Overview', () => <RuleOverviewPage />)
  .add('Rule Detail', () => <RuleDetailPage parameters={{ ruleId: '1' }} />)
  .add('Rule Creation', () => <RuleCreationPage />)
  .add('Rule Editing', () => <RuleEditingPage parameters={{ ruleId: '1' }} />)
