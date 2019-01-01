import React from 'react'
import { RenderFunction } from '@storybook/react';
import StyleWrapper from '@/StyleWrapper'

const StoryWrapper = (story: RenderFunction) => (
  <StyleWrapper>
    {story()}
  </StyleWrapper>
)

export default StoryWrapper
