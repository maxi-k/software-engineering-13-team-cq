import React from 'react'
import styled from 'styled-components'

const StyledSection = styled.section`
    padding: 1rem;
`

export default () => (
    <StyledSection>
        <h2>Welcome to the BMW CNS Storybook!</h2>
        <p>
            This view is used to showcase some of the components, separated from their surroundings and any logic.
        </p>
        <p>
            The goal is to conciously design every possible state (active, inactive, loading, error, etc.), <br/>
            while at the same time separating logic from looks.
        </p>
        <p>
            Please select a component from the sidebar on the left. Note that the components listed 
            under <strong>Fleetdata</strong> <br/> are part of the package provided by MbW, and are not created or modified by 
            the CNS team.
        </p>
    </StyledSection>
)
