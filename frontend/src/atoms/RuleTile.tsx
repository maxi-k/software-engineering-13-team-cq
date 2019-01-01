import React from 'react'
import styled from 'styled-components'
import Typography from '@material-ui/core/Typography'

import { NotificationRule_Overview, VehicleDataType } from '@/model/Rule'
import RuleTileBase from './RuleTileBase'
import RuleIcon from '@/icons/RuleIcon'
import { mediumgray } from '@fleetdata/shared/styles/variables';

interface RuleTileProps
  extends React.ButtonHTMLAttributes<Props> {
  rule: NotificationRule_Overview
}

interface RuleTileIconsProps {
  dataSources: VehicleDataType[]
}

const StyledRuleTile = styled(RuleTileBase)`
  cursor: pointer;
`

const RuleTileSeparator = styled.hr`
  border: none;
  visibility: hidden;
`

const RuleTileIcons: React.SFC<RuleTileIconsProps> = ({ dataSources }) => (
  <>
    {dataSources.map((source: VehicleDataType) => (
      <RuleIcon key={source} type={source} />
    ))
    }
  </>
)

const RuleTile: React.SFC<RuleTileProps> = ({ rule, ...props }) => (
  <StyledRuleTile {...props}>
    <Typography variant="h6">{rule.name}</Typography>
    <RuleTileSeparator />
    <Typography variant="subtitle1">{rule.aggregatorDescription}</Typography>
    <RuleTileSeparator />
    <RuleTileIcons dataSources={rule.dataSources} />
  </StyledRuleTile>
)

export default RuleTile
