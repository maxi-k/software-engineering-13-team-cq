import React from 'react'
import styled from 'styled-components'
import Typography from '@material-ui/core/Typography'

import { NotificationRuleOverview, VehicleDataType } from '@/model'
import RuleTileBase, { RuleTileProps as TileBaseProps } from './RuleTileBase'
import RuleIcon from '@/modules/shared/components/RuleIcon'
import AggregatorDescription from '@/modules/shared/components/AggregatorDescription'
import { getRuleDataSources } from '@/services/rule-service';

export interface RuleTileProps
  extends TileBaseProps {
  rule: NotificationRuleOverview
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

const RuleTile: React.SFC<RuleTileProps & React.HTMLProps<any>> = ({ rule, ...props }) => (
  <StyledRuleTile {...props}>
    <Typography variant="h6">{rule.name}</Typography>
    <RuleTileSeparator />
    <Typography variant="subtitle1">
      <AggregatorDescription aggregator={rule.aggregator} labelOnly />
    </Typography>
    <RuleTileSeparator />
    <RuleTileIcons dataSources={getRuleDataSources(rule)} />
  </StyledRuleTile>
)

export default RuleTile
