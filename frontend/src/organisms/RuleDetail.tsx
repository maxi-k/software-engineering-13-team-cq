import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { FetchingAttributes, NotificationRuleDetail } from '@/model'

import Typography from '@material-ui/core/Typography'

import ErrorMessage from '@/atoms/ErrorMessage'
import LoadingIndicator from '@/atoms/LoadingIndicator'
import RuleRecipientTag from '@/atoms/RuleRecipientTag'
import FieldDescriptor from '@/molecules/FieldDescriptor'
import RuleIcon from '@/icons/RuleIcon'

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
        <FormattedMessage id="cns.message.fetch.error" />
      } />
    )
  }
  if (isFetching || typeof rule === 'undefined' || rule === null) {
    return <LoadingIndicator isCentral={true} />
  }

  return (
    <StyledRuleDetail {...props}>
      <Typography variant="h5" style={{ paddingLeft: '1rem' }}>
        <FormattedMessage id="cns.rule.label" />{' '}
        "{rule.name}"
      </Typography>
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
                  type={dataSource}
                  label={`cns.vehicle.status.${dataSource.toLowerCase()}.label`} />)
              )}
            </>
            } />
        </StyledInfoBlock>
      </StyledRuleInformation>
    </StyledRuleDetail>
  )
}

export default RuleDetail