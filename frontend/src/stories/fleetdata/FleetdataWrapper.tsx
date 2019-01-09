import React from 'react'
import { RenderFunction } from '@storybook/react';
import styled from 'styled-components'

const StyledWrapper = styled.div`
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    align-content: center;
`

const FleetdataWrapper = (story: RenderFunction) => {
    return (
        <StyledWrapper>
            { story() }
        </StyledWrapper>
    )

}

export default FleetdataWrapper