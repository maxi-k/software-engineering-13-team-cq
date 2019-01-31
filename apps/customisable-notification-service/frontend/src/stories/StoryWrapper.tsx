import React from 'react'
import { RenderFunction } from '@storybook/react';
import StyleWrapper from '@/StyleWrapper'
import IntlWrapper from '@/IntlWrapper'
import StoreWrapper from '@/StoreWrapper'

const StoryWrapper = (story: RenderFunction) => (
  <StyleWrapper>
    <StoreWrapper>
      <IntlWrapper>
        {story()}
      </IntlWrapper>
    </StoreWrapper>
  </StyleWrapper>
)

export default StoryWrapper
