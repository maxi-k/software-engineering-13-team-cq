import React from 'react'
import styled from 'styled-components'
import { rem } from 'polished';
import {
  Container,
  InnerContainer,
  StyledSelectorContainer,
} from '@fleetdata/shared/styled-components/navigation.style';

interface RuleTileProps
  extends React.HTMLAttributes {

}

const RuleTileContainer = styled(InnerContainer)`
  height: ${rem(160)};
`

const RuleTileContent = styled.div`
    text-align: center;
`

/* React.[S]tateless[F]unctional[C]omponent */
const RuleTileBase: React.SFC<RuleTileProps> = ({ children, className }) => (
  <StyledSelectorContainer>
    <Container elevation={0} className={className}>
      <RuleTileContainer>
        <RuleTileContent className="nav-label">
          {children}
        </RuleTileContent>
      </RuleTileContainer>
    </Container>
  </StyledSelectorContainer>
)

export default RuleTileBase
