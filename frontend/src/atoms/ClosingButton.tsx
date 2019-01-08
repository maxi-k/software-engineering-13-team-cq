import React from 'react'
import styled from 'styled-components'
import BaseTile, { RuleTileProps } from './RuleTileBase'
import CloseIcon from '@fleetdata/shared/components/icons/close.icon'

const StyledCloseRuleTile = styled(BaseTile)`
  cursor: pointer;
`

const ClosingButton: React.SFC<RuleTileProps> = (props) => (
  <StyledCloseRuleTile {...props}>
    <CloseIcon />
  </StyledCloseRuleTile>
)


export default ClosingButton
