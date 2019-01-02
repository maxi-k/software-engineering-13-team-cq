import React from 'react'
import { storiesOf } from '@storybook/react'
import SingleComponentWrapper from '../SingleComponentWrapper'
import LoadingIndicator from '@/atoms/LoadingIndicator'

storiesOf('General Components', module)
  .addDecorator(SingleComponentWrapper)
  .add('Small Loading Indicator', () => <LoadingIndicator />, {
    notes: {
      markdown: ` # Loading Indicator
         This indicator is imported directly from material-ui.
    `}
  })
  .add('Large Loading Indicator', () => <LoadingIndicator size={80} />)
