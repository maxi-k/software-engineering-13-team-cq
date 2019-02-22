import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'
import { messageFromError } from '@/services/response-service'
import { media } from '@/fleetdata/utils/media-query'

import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import { BMWButton } from '@fleetdata/shared/components/button';
import EditIcon from '@fleetdata/shared/components/icons/edit.icon'
import DeleteIcon from '@fleetdata/shared/components/icons/delete.icon'

import EmbeddedIcon from '@/modules/shared/components/EmbeddedIcon'
import ErrorMessage from '@/modules/shared/components/ErrorMessage'
import LoadingIndicator from '@/modules/shared/components/LoadingIndicator'
import ViewHeader from '@/modules/shared/components/ViewHeader'
import BackButton from '@/modules/shared/parts/BackButton'
import RuleIcon from '@/modules/shared/components/RuleIcon'
import FlexBar from '@/modules/shared/components/FlexBar'

import FieldDescriptor from '../components/FieldDescriptor'
import PropertyTag from '../components/PropertyTag'
import RuleRecipientTag from '../components/RuleRecipientTag'
import RuleConditionDetail from '../components/RuleConditionDetail'

interface RuleDetailAttributes {
  rule: NotificationRuleDetail,
  toggleEditRule(rule: NotificationRuleDetail): void,
  toggleDeleteRule(rule: NotificationRuleDetail): void
}

type RuleDetailProps = FetchingAttributes
  & RuleDetailAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetail = styled.div`
`

const StyledRuleInformation = styled.div`
 display: flex;
 flex-direction: row;

 ${media.md`
    flex-direction: column;
 `};
`

const StyledInfoBlock = styled.div`
  flex-basis: 50%;
  flex-shrink: 1;
  padding: 1rem;
`

const StyledFieldSeparator = styled.div`
  padding: 1rem;
`

const FlexButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;

  ${BMWButton} {
    margin-right: 1rem;
  }
`

const RuleDetail: React.SFC<RuleDetailProps> = ({
  isFetching, hasFetchError, rule,
  toggleEditRule, toggleDeleteRule,
  ...props
}) => {

  if (hasFetchError) {
    return (
      <ErrorMessage message={
        <FormattedMessage id={messageFromError(hasFetchError)} />
      } />
    )
  }
  if (isFetching || typeof rule === 'undefined' || rule === null) {
    return <LoadingIndicator isCentral={true} />
  }

  const onClickDeleteRule = () => toggleDeleteRule(rule)
  const onClickEditRule = () => toggleEditRule(rule)

  return (
    <StyledRuleDetail {...props}>
      <ViewHeader
        style={{ padding: '1rem' }}
        title="cns.page.ruleEdit.title"
        titleSuffix={` '${rule.name}'`} />
      <FlexBar padded>
        <FlexButtonWrapper>
          <BMWButton
            icon={<EmbeddedIcon component={EditIcon} />}
            onClick={onClickEditRule} >
            <FormattedMessage id="cns.page.ruleEdit.title" />
          </BMWButton>
          <BMWButton
            icon={<EmbeddedIcon component={DeleteIcon} />}
            onClick={onClickDeleteRule} >
            <FormattedMessage id="cns.page.ruleDelete.title" />
          </BMWButton>
        </FlexButtonWrapper>
        <BackButton goToHome />
      </FlexBar>
      <StyledFieldSeparator />
      <StyledRuleInformation>
        <StyledInfoBlock>
          <FieldDescriptor
            label={"cns.rule.field.name.label"}
            content={rule.name} />
          <StyledFieldSeparator />
          <FieldDescriptor
            label={"cns.rule.field.description.label"}
            content={rule.description} />
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
                rule.recipients.map(
                  (recipient) => <RuleRecipientTag
                    key={recipient.value}
                    recipient={recipient} />)
              }
            </>
            } />
          <StyledFieldSeparator />
          <FieldDescriptor
            label={"cns.rule.field.condition.label"}
            content={<RuleConditionDetail condition={rule.condition} />}
          />
        </StyledInfoBlock>
        <StyledInfoBlock>
          <FieldDescriptor
            label={"cns.rule.field.affectedFleets.label"}
            content={rule.applyToAllFleets
              ? <FormattedMessage id="cns.rule.field.affectedFleets.value.all.label" />
              : <>{
                rule.fleets.map((fleet) => (
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
              {rule.dataSources.map((dataSource) => (
                <RuleIcon
                  key={dataSource}
                  type={dataSource}
                  label={`cns.vehicle.status.${dataSource.toLowerCase()}.label`} />)
              )}
            </>
            } />
        </StyledInfoBlock>
      </StyledRuleInformation>
    </StyledRuleDetail >
  )
}

export default RuleDetail
