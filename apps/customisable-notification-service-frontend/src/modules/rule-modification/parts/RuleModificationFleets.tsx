import React from 'react'
import { connect } from 'react-redux'
import { FormattedMessage } from 'react-intl'
import { mergeFleetInformation } from '@/services/car-park-service'
import {
  RuleModificationStepView,
  createCheckboxUpdater,
  createMultiValueUpdater
} from '../modification-common'
import { Fleet, NotificationRuleInProgress } from '@/model'
import { StateMapper } from '@/state/connector'
import { carParkFleetsSelector } from '@/state/selectors'

import Switch from '@material-ui/core/Switch'
import FormControlLabel from '@material-ui/core/FormControlLabel'

import FleetSelector from '../components/FleetSelector'

interface RuleModificationFleetSelectorAttributes {
  rule: NotificationRuleInProgress,
  updater(...event: any): void
}

interface RuleModificationFleetSelectorStateAttributes {
  fleets: { [key: string]: Fleet }
}

type RuleModificationFleetSelectorProps =
  RuleModificationFleetSelectorAttributes
  & RuleModificationFleetSelectorStateAttributes

const convertFleetsToOptions = (fleets: Fleet[]) => (
  fleets.map((fleet) => ({ label: fleet.name, value: fleet, key: fleet.fleetId }))
)

const mapStateToProps: StateMapper<RuleModificationFleetSelectorAttributes, RuleModificationFleetSelectorStateAttributes> = (state, props) => ({
  fleets: carParkFleetsSelector(state)
})

const RuleModificationFleetSelector: React.SFC<RuleModificationFleetSelectorProps> = (
  { rule, updater, fleets }
) => {
  return (
    <FleetSelector
      value={convertFleetsToOptions(mergeFleetInformation(fleets, rule.fleets || []))}
      options={convertFleetsToOptions(Object.values(fleets))}
      onChange={updater}
    />
  )
}

const ConnectedRuleModificationFleetSelector = connect(mapStateToProps)(RuleModificationFleetSelector)

const RuleModificationFleets: RuleModificationStepView = (
  { inProgressRule, updateField }
) => (
    <div>
      <FormControlLabel
        control={
          <Switch checked={inProgressRule.applyToAllFleets}
            onChange={createCheckboxUpdater(updateField)('applyToAllFleets')}
            value="applyToAllFleets" />
        }
        label={<FormattedMessage id="cns.rule.field.applyToAllFleets.label" />} />
      {!inProgressRule.applyToAllFleets &&
        <ConnectedRuleModificationFleetSelector
          rule={inProgressRule}
          updater={createMultiValueUpdater(updateField)('fleets')} />
      }
    </div>
  )

export default RuleModificationFleets
