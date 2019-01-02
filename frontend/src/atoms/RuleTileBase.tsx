import React from 'react'
import styled from 'styled-components'
import { rem } from 'polished';
import {
  Container,
  InnerContainer
} from '@fleetdata/shared/styled-components/navigation.style';

export interface RuleTileParameters {

}
export type RuleTileProps = RuleTileParameters & React.HTMLAttributes<HTMLDivElement>

const RuleTileContainer = styled(InnerContainer)`
  height: ${rem(160)};
  width: ${rem(240)};
`

const RuleTileContent = styled.div`
    text-align: center;
`

/* React.[S]tateless[F]unctional[C]omponent */
const RuleTileBase: React.SFC<RuleTileProps> = ({ children, ...props }) => (
  <Container elevation={0} {...props}>
    <RuleTileContainer>
      <RuleTileContent className="nav-label">
        {children}
      </RuleTileContent>
    </RuleTileContainer>
  </Container>
)

export default RuleTileBase
