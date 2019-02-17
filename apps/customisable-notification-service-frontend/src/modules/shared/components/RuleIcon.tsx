import React from 'react'
import styled from 'styled-components'

import { FormattedMessage } from 'react-intl'
import { VehicleDataType } from '@/model'

interface RuleIconProps {
  type: VehicleDataType,
  size?: number,
  label?: string
}

const StyledRuleIcon = styled("div") <{ label?: string }>`
  display: ${props => props.label ? 'flex' : 'inline-block'};
  ${props => props.label && 'align-items: center;'}
  padding: 5px;

  span {
    padding-left: 7px;
  }
`

const RuleIcon: React.SFC<RuleIconProps> = ({ type, size = 20, ...props }) => {
  const source = "/assets/img/" + type + ".png";
  return (
    <StyledRuleIcon {...props}>
      {<img style={{ width: `${size}px`, height: `${size}px` }} src={source} />}
      {props.label &&
        <span>
          <FormattedMessage id={props.label} />
        </span>}
    </StyledRuleIcon>
  )
}

export default RuleIcon
