import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'

import StoryWrapper from '../StoryWrapper'
import SingleComponentWrapper from '../SingleComponentWrapper'
import { mockedRuleDetail } from '@/services/rule-service'

import PropertyTag from '@/modules/rule-detail/components/PropertyTag'
import { NotificationRecipient, NotificationRecipientType, FetchingAttributes } from '@/model'

import RuleRecipientTag from '@/modules/rule-detail/components/RuleRecipientTag'
import RuleDetailView from '@/modules/rule-detail/views/RuleDetail'

const notificationRecipient: NotificationRecipient = {
  type: NotificationRecipientType.Email,
  value: 'test@example.com'
}

storiesOf('Rule Detail / General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Recipient Tag', () => <RuleRecipientTag recipient={notificationRecipient} />)
  .add('Property Tag', () => (
    <PropertyTag>
      Property Label
    </PropertyTag>
  ))

const PartialRuleDetail: React.SFC<FetchingAttributes> = ({ isFetching, hasFetchError }) => (
  <RuleDetailView
    rule={mockedRuleDetail(42)}
    toggleDeleteRule={action('Toggle Delete Rule')}
    toggleEditRule={action('Toggle Edit Rule')}
    isFetching={isFetching}
    hasFetchError={hasFetchError}
  />
)

storiesOf('Rule Detail / Complete', module)
  .addDecorator(StoryWrapper)
  .add('Fetching', () => <PartialRuleDetail isFetching={true} hasFetchError={false} />)
  .add('With Error', () => <PartialRuleDetail isFetching={false} hasFetchError={true} />)
  .add('Success', () => <PartialRuleDetail isFetching={false} hasFetchError={false} />)
