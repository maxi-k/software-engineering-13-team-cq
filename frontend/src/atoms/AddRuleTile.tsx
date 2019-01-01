import React from 'react'
import styled from 'styled-components'
import BaseTile from './RuleTileBase'
import AddIcon from '@fleetdata/shared/components/icons/add.icon'

const StyledAddRuleTile = styled(BaseTile)`
  cursor: pointer;
`

const AddRuleTile: React.SFC<Props> = ({ onClick }) => (
  <StyledAddRuleTile>
    <AddIcon />
  </StyledAddRuleTile>
)


export default AddRuleTile
