import React from 'react'
import styled from 'styled-components'
import BaseTile, { RuleTileProps } from './RuleTileBase'
import AddIcon from '@fleetdata/shared/components/icons/add.icon'

const StyledAddRuleTile = styled(BaseTile)`
  cursor: pointer;
`

const AddRuleTile: React.SFC<RuleTileProps> = (props) => (
  <StyledAddRuleTile {...props}>
    <AddIcon />
  </StyledAddRuleTile>
)


export default AddRuleTile
