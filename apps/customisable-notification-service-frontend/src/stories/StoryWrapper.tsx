import React from 'react'
import { RenderFunction } from '@storybook/react';
import StyleWrapper from '@/wrappers/StyleWrapper'
import IntlWrapper from '@/wrappers/IntlWrapper'
import StoreWrapper from '@/wrappers/StoreWrapper'

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
