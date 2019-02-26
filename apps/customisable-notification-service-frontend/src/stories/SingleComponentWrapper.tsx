import React from 'react'
import { RenderFunction } from '@storybook/react';
import styled from 'styled-components'
import StoryWrapper from './StoryWrapper'

const StyledWrapper = styled.div`
    height: calc(100vh - 4rem);
    margin: 2rem;
    display: flex;
    justify-content: center;
    align-items: center;
    align-content: center;
    flex-direction: column;
`

const SingleComponentWrapper = (story: RenderFunction) => (
  <StyledWrapper>
    {StoryWrapper(story)}
  </StyledWrapper>
)

export default SingleComponentWrapper
