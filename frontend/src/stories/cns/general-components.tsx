import React from 'react'
import { FormattedMessage } from 'react-intl';
import { storiesOf } from '@storybook/react'

import { handledStatus, messageFromError } from '@/services/response-service'
import StoryWrapper from '../StoryWrapper'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import ErrorMessage from '@/atoms/ErrorMessage'

storiesOf('General Components', module)
  .addDecorator(StoryWrapper)
  .add('Small Loading Indicator', () => <LoadingIndicator />, {
    notes: {
      markdown: ` # Loading Indicator
         This indicator is imported directly from material-ui.
      `}
  })
  .add('Large Loading Indicator', () => <LoadingIndicator size={80} />)
  .add('Central Loading Indicator', () => <LoadingIndicator isCentral={true} />)
  .add('Simple Error Message', () =>
    <ErrorMessage message="This is an error message with a string message" />)
  .add('Requests Error Messages', () =>
    <>
      {Object.keys(handledStatus).map((code) =>
        <ErrorMessage key={code}
          message={<> <FormattedMessage id={messageFromError({ status: parseInt(code) })} /> ({code}) </>}
          style={{ marginBottom: '1rem' }} />
      )}
    </>
  )
  .add('Complex Error Message', () =>
    <ErrorMessage message={
      <>
        <span>
          This is an error message
          with a <strong>react node</strong> as message!
          &nbsp;
        </span>
        <LoadingIndicator size={20} color="inherit" />
        <span>&nbsp; 🔥 🔥 </span>
      </>
    } />)
