import React from 'react'
import { storiesOf } from '@storybook/react'
// import { action } from '@storybook/addon-actions'

// import StoryWrapper from '../StoryWrapper'
import SingleComponentWrapper from '../SingleComponentWrapper'

import { NotificationRecipient, NotificationRecipientType } from '@/model'

import RuleRecipientTag from '@/modules/rule-detail/components/RuleRecipientTag'

const notificationRecipient: NotificationRecipient = {
  type: NotificationRecipientType.Email,
  value: 'test@example.com'
}

storiesOf('Rule Detail / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Recipient Tag', () => <RuleRecipientTag recipient={notificationRecipient} />)
