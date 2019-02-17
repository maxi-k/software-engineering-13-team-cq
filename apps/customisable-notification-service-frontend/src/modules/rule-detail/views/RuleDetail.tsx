import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'
import { messageFromError } from '@/services/response-service'
import { media } from '@/fleetdata/utils/media-query'

import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import ErrorMessage from '@/modules/shared/components/ErrorMessage'
import LoadingIndicator from '@/modules/shared/components/LoadingIndicator'
import ViewHeader from '@/modules/shared/components/ViewHeader'
import BackButton from '@/modules/shared/parts/BackButton'
import RuleIcon from '@/modules/shared/components/RuleIcon'

import FieldDescriptor from '../components/FieldDescriptor'
import PropertyTag from '../components/PropertyTag'
import RuleRecipientTag from '../components/RuleRecipientTag'

interface RuleDetailAttributes {
  rule: NotificationRuleDetail
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

const RuleDetail: React.SFC<RuleDetailProps> = ({
  isFetching, hasFetchError, rule, ...props
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

  return (
    <StyledRuleDetail {...props}>
      <ViewHeader
        style={{ padding: '1rem' }}
        title="cns.page.ruleEdit.title"
        titleSuffix={` '${rule.name}'`} >
        <BackButton />
      </ViewHeader>
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
        </StyledInfoBlock>
        <StyledInfoBlock>
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
