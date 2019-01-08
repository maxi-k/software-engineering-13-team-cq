import React from 'react'
import styled from 'styled-components'

import { VehicleDataType } from '@/model/Rule'

interface RuleIconProps {
  type: VehicleDataType
}

const StyledRuleIcon = styled.div`
    display: inline-block;
    padding: 0px 5px;
`
const RuleIcon: React.SFC<RuleIconProps> = ({ type, ...props }) => {
  const source = "/assets/img/" + type + ".png";
  return (
    <StyledRuleIcon {...props}>{<img style={{width: '20px', height: '20px'}} src={source}/>}</StyledRuleIcon>
  )
}

export default RuleIcon
