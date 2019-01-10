import React from 'react'
import { storiesOf } from '@storybook/react'
import StoryWrapper from '../StoryWrapper'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import ErrorMessage from '@/atoms/ErrorMessage'
import NextButton from '@/atoms/NextButton'
import BackButton from '@/atoms/BackButton'
import { action } from '@storybook/addon-actions'

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
  .add('Next Button', () => <NextButton onClick={action('Next!')} />)
  .add('Back Button', () => <BackButton onClick={action('Back!')} />)
  .add('Simple Error Message', () =>
    <ErrorMessage message="This is an error message with a string message" />)
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
