import React from 'react'
import styled from 'styled-components'

import AddIcon from '@fleetdata/shared/components/icons/add.icon'
import BaseTile, { RuleTileProps } from './RuleTileBase'

const StyledAddRuleTile = styled(BaseTile)`
  cursor: pointer;
`

const AddRuleTile: React.SFC<RuleTileProps> = (props) => (
  <StyledAddRuleTile
    data-test-id="add-rule-option"
    {...props}>
    <AddIcon />
  </StyledAddRuleTile>
)

export default AddRuleTile
