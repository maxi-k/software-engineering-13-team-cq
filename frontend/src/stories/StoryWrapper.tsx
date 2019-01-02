import React from 'react'
import { RenderFunction } from '@storybook/react';
import StyleWrapper from '@/StyleWrapper'
import IntlWrapper from '@/IntlWrapper'

const StoryWrapper = (story: RenderFunction) => (
  <StyleWrapper>
    <IntlWrapper>
      {story()}
    </IntlWrapper>
  </StyleWrapper>
)

export default StoryWrapper
