import React from 'react'
import { connect } from 'react-redux'
import { FormattedMessage } from 'react-intl'
import {
  RuleCreationStepView,
  createCheckboxUpdater,
  createMultiValueUpdater
} from './common'
import { SelectValue, Fleet, NotificationRuleDetail } from '@/model'
import { StateMapper } from '@/state/connector'
import { carParkFleetsSelector } from '@/state/selectors'

import Switch from '@material-ui/core/Switch'
import FormControlLabel from '@material-ui/core/FormControlLabel'

import FleetSelector from '@/atoms/FleetSelector'

interface RuleCreationFleetSelectorAttributes {
  rule: Partial<NotificationRuleDetail>,
  updater(...event: any): void
}

interface RuleCreationFleetSelectorStateAttributes {
  fleets: { [key: string]: Fleet }
}

type RuleCreationFleetSelectorProps =
  RuleCreationFleetSelectorAttributes
  & RuleCreationFleetSelectorStateAttributes

const convertFleetsToOptions = (fleets: Fleet[]) => (
  fleets.map((fleet) => ({ label: fleet.name, value: fleet.id }))
)

const convertOptionsToFleets = (
  options: SelectValue[],
  fleets: { [key: string]: Fleet }
) => (options.map((option) => (fleets[option.value])))

const convertFleetSelectorUpdater = (
  updater: RuleCreationFleetSelectorAttributes['updater'],
  fleets: { [key: string]: Fleet }
) => (
    (selected: SelectValue[], newlySelected: SelectValue) => (
      updater(convertOptionsToFleets(selected, fleets))
    )
  )

const mapStateToProps: StateMapper<RuleCreationFleetSelectorAttributes, RuleCreationFleetSelectorStateAttributes> = (state, props) => ({
  fleets: carParkFleetsSelector(state)
})

const RuleCreationFleetSelector: React.SFC<RuleCreationFleetSelectorProps> = (
  { rule, updater, fleets }
) => {
  return (
    <FleetSelector
      value={convertFleetsToOptions(rule.fleets || [])}
      options={convertFleetsToOptions(Object.values(fleets))}
      onChange={convertFleetSelectorUpdater(updater, fleets)}
    />
  )
}

const ConnectedRuleCreationFleetSelector = connect(mapStateToProps)(RuleCreationFleetSelector)

const RuleCreationFleets: RuleCreationStepView = (
  { inProgressRule, updateField }
) => (
    <div>
      <FormControlLabel
        control={
          <Switch checked={inProgressRule.applyToAllFleets}
            onChange={createCheckboxUpdater(updateField, 'applyToAllFleets')}
            value="applyToAllFleets" />
        }
        label={<FormattedMessage id="cns.rule.field.applyToAllFleets.label" />} />
      {!inProgressRule.applyToAllFleets &&
        <ConnectedRuleCreationFleetSelector
          rule={inProgressRule}
          updater={createMultiValueUpdater(updateField, 'fleets')} />
      }
    </div>
  )

export default RuleCreationFleets
