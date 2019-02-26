import React from 'react'
import styled from 'styled-components'
import { connect } from 'react-redux'
import { FormattedMessage } from 'react-intl'

import { media } from '@/fleetdata/utils/media-query'
import { mergeFleetInformation } from '@/services/car-park-service'
import { getRuleDataSources } from '@/services/rule-service'
import { StateMapper } from '@/state/connector'
import { carParkFleetsSelector } from '@/state/selectors'
import { NotificationRuleDetail, Fleet, AggregatorStrategy } from '@/model'

import RuleIcon from '@/modules/shared/components/RuleIcon'
import AggregatorDescription from '@/modules/shared/components/AggregatorDescription'

import FieldDescriptor from '../components/FieldDescriptor'
import PropertyTag from '../components/PropertyTag'
import RuleRecipientTag from '../components/RuleRecipientTag'
import RuleConditionDetail from '../components/RuleConditionDetail'

interface RuleDetailsAttributes {
  rule: Partial<NotificationRuleDetail>
}

interface StateAttributes {
  fleets: { [key: string]: Fleet }
}

export type RuleDetailsProps = RuleDetailsAttributes
  & StateAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleInformation = styled.div`
 display: flex;
 flex-direction: row;

 ${media.md`
    flex-direction: column;
 `};
`

export const StyledInfoBlock = styled.div`
  flex-basis: 50%;
  flex-shrink: 1;
  padding: 1rem;
`

export const StyledFieldSeparator = styled.div`
  padding: 1rem;
`

const RuleDetails: React.SFC<RuleDetailsProps> = ({ rule, fleets, ...otherProps }) => (
  <StyledRuleInformation {...otherProps} >
    <StyledInfoBlock>
      <FieldDescriptor
        label={"cns.rule.field.name.label"}
        content={rule.name || <FormattedMessage id="cns.rule.field.value.unspecified.label" />} />
      <StyledFieldSeparator />
      <FieldDescriptor
        label={"cns.rule.field.description.label"}
        content={rule.description || <FormattedMessage id="cns.rule.field.value.unspecified.label" />} />
      <StyledFieldSeparator />
      <FieldDescriptor
        label={"cns.rule.field.recipients.label"}
        content={<>
          {
            rule.ownerAsAdditionalRecipient &&
            <PropertyTag>
              <FormattedMessage id="cns.rule.field.owner.label" />
            </PropertyTag>
          }
          {
            (rule.recipients || []).map(
              (recipient) => <RuleRecipientTag
                key={recipient.value}
                recipient={recipient} />)
          }
        </>
        } />
      <StyledFieldSeparator />
      <FieldDescriptor
        label={"cns.rule.field.condition.label"}
        content={rule.condition
          ? <RuleConditionDetail condition={rule.condition} />
          : <FormattedMessage id="cns.rule.field.value.unspecified.label" />} />
    </StyledInfoBlock>
    <StyledInfoBlock>
      <FieldDescriptor
        label={"cns.rule.field.affectedFleets.label"}
        content={rule.applyToAllFleets
          ? <FormattedMessage id="cns.rule.field.affectedFleets.value.all.label" />
          : <>{
            mergeFleetInformation(fleets, rule.fleets || []).map((fleet) => (
              <PropertyTag key={fleet.fleetId}>
                {fleet.name} (
                    <FormattedMessage
                  id="cns.rule.field.affectedFleets.value.vehicleCount.label"
                  values={{ vehicleCount: fleet.numberOfVehicles || 0 }} />)
                  </PropertyTag>
            ))
          } </>
        } />
      <StyledFieldSeparator />
      <FieldDescriptor
        label={"cns.rule.field.dataSources.label"}
        content={<>
          {getRuleDataSources(rule)
            .filter((dataSource) => dataSource !== null && typeof dataSource !== 'undefined')
            .map((dataSource) => (
              <RuleIcon
                key={dataSource}
                type={dataSource}
                label={`cns.vehicle.status.${dataSource.toLowerCase()}.label`} />)
            )}
        </>
        } />
      <FieldDescriptor
        label={"cns.rule.field.aggregator.label"}
        content={
          <AggregatorDescription aggregator={(rule.aggregator || { strategy: AggregatorStrategy.Immediate })} />
        } />
    </StyledInfoBlock>
  </StyledRuleInformation>
)

const mapStateToProps: StateMapper<RuleDetailsAttributes, StateAttributes> = (state, props) => ({
  fleets: carParkFleetsSelector(state)
})

export default connect(mapStateToProps)(RuleDetails)
