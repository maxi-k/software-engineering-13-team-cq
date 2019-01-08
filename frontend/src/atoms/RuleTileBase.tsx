import React from 'react'
import styled from 'styled-components'
import { rem } from 'polished';
import {
  Container,
  InnerContainer
} from '@fleetdata/shared/styled-components/navigation.style';

export interface RuleTileParameters {
  padTile?: number
}
export type RuleTileProps = RuleTileParameters & React.HTMLAttributes<HTMLDivElement>

const RuleTileWrapper = styled("div") <RuleTileProps>`
  padding: ${props => props.padTile ? rem(props.padTile) : 0};
`

const RuleTileContainer = styled(InnerContainer)`
  height: ${rem(160)};
  width: ${rem(240)};
`

const RuleTileContent = styled.div`
  text-align: center;
`

/* React.[S]tateless[F]unctional[C]omponent */
const RuleTileBase: React.SFC<RuleTileProps> = ({ children, onClick, padTile, ...props }) => (
  <RuleTileWrapper {...props} padTile={padTile}>
    <Container elevation={0} {...props} onClick={onClick}>
      <RuleTileContainer>
        <RuleTileContent className="nav-label">
          {children}
        </RuleTileContent>
      </RuleTileContainer>
    </Container>
  </RuleTileWrapper>
)

export default RuleTileBase
