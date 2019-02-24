import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { applyLazy } from '@/utilities/function-util'
import { AggregatorStrategy } from '@/model'

import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import Typography from '@material-ui/core/Typography';
import CheckBoxCheckedIcon from '@material-ui/icons/CheckBox'
import CheckBoxIcon from '@material-ui/icons/CheckBoxOutlineBlank'

import { AggregatorComponentAttributes } from '../aggregator-common'
import CountingAggregator from './CountingAggregator'

interface AggregatorSelectorAttributes
  extends AggregatorComponentAttributes {
  setAggregatorStrategy: (aggregatorStrategy: AggregatorStrategy) => void
}

type AggregatorSelectorProps = AggregatorSelectorAttributes
  & React.HTMLAttributes<HTMLDivElement>

const expandedIconStyle = {
  transform: 'rotate(180deg)'
}

const AggregatorDetailWrapper = styled.div`
  display: flex;
  flex-direction: column;
`

const AggregatorComponentWrapper = styled.div`

`

const aggregatorComponentForStrategy = (
  strategy: AggregatorStrategy
): React.ComponentType<AggregatorComponentAttributes> => {
  switch (strategy) {
    case AggregatorStrategy.Counting:
      return CountingAggregator
    case AggregatorStrategy.Immediate:
    default:
      return (props) => <div />
  }
}

const AggregatorSelector: React.SFC<AggregatorSelectorProps> = ({
  aggregator, setAggregatorStrategy, setAggregatorValue, ...htmlProps
}) => {

  return (
    <div {...htmlProps}>
      {
        Object.values(AggregatorStrategy).filter((value) => typeof value === 'number').map((strategy) => {
          const doExpand = strategy === aggregator.strategy
          const Component = aggregatorComponentForStrategy(strategy)
          return (
            <ExpansionPanel key={strategy} expanded={doExpand}
              onChange={applyLazy(setAggregatorStrategy, strategy)}>
              <ExpansionPanelSummary expandIcon={doExpand
                ? <CheckBoxCheckedIcon color="primary" style={expandedIconStyle} />
                : <CheckBoxIcon />}>
                <Typography variant="overline">
                  <FormattedMessage id={`cns.rule.field.aggregator.strategy.value.${AggregatorStrategy[strategy].toLowerCase()}.label`} />
                </Typography>
              </ExpansionPanelSummary>
              <ExpansionPanelDetails>
                <AggregatorDetailWrapper>
                  <Typography variant="subtitle2" paragraph={true}>
                    <FormattedMessage id={`cns.rule.field.aggregator.strategy.value.${AggregatorStrategy[strategy].toLowerCase()}.description`} />
                  </Typography>
                  <AggregatorComponentWrapper>
                    <Component aggregator={aggregator} setAggregatorValue={setAggregatorValue} />
                  </AggregatorComponentWrapper>
                </AggregatorDetailWrapper>
              </ExpansionPanelDetails>
            </ExpansionPanel>
          )
        }
        )
      }
    </div>
  )
}

export default AggregatorSelector
