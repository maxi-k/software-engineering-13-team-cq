import React from 'react'
import { FormattedMessage } from 'react-intl'

import { applyLazy } from '@/utilities/function-util'
import { Aggregator, AggregatorStrategy } from '@/model'

import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import Typography from '@material-ui/core/Typography';
import CheckBoxCheckedIcon from '@material-ui/icons/CheckBox'
import CheckBoxIcon from '@material-ui/icons/CheckBoxOutlineBlank'

interface AggregatorComponentAttributes {
  aggregator: Aggregator,
  setAggregatorValue: (aggregatorValue: string) => void
}

interface AggregatorSelectorAttributes
  extends AggregatorComponentAttributes {
  setAggregatorStrategy: (aggregatorStrategy: AggregatorStrategy) => void
}

type AggregatorSelectorProps = AggregatorSelectorAttributes
  & React.HTMLAttributes<HTMLDivElement>

const expandedIconStyle = {
  transform: 'rotate(180deg)'
}

const aggregatorComponentForStrategy = (strategy: AggregatorStrategy): React.ComponentType<AggregatorComponentAttributes> => {
  return (props) => <div />
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
                <Typography variant="subtitle2">
                  <FormattedMessage id={`cns.rule.field.aggregator.strategy.value.${AggregatorStrategy[strategy].toLowerCase()}.description`} />
                </Typography>
                <Component aggregator={aggregator} setAggregatorValue={setAggregatorValue} />
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
