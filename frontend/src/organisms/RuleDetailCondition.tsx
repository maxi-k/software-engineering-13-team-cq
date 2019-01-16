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
import NextButton from '@/atoms/NextButton'
import ClosingButton from '@/atoms/ClosingButton'

export type FinishConditionType = (event: React.SyntheticEvent<any, any>) => void
export type AbortConditionType = (event: React.SyntheticEvent<any, any>) => void

export interface RuleDetailConditionAttributes {
  rule: NotificationRuleDetail
  finishCondition: FinishConditionType
  abortCondition: AbortConditionType
}

export type RuleDetailConditionProps = FetchingAttributes
  & RuleDetailConditionAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetailCondition = styled.div`
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
const conditionFinisher = (finishCondition: FinishConditionType) =>
    (e: React.SyntheticEvent<any, any>): void =>
      finishCondition(e)

const conditionAborter = (abortCondition: AbortConditionType) =>
    (e: React.SyntheticEvent<any, any>): void =>
      abortCondition(e)

const RuleDetailCondition: React.SFC<RuleDetailConditionProps> = ({
  isFetching, hasFetchError, rule, finishCondition, abortCondition, ...props
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
    <StyledRuleDetailCondition {...props}>
      <Typography variant="h5" style={{ paddingLeft: '1rem' }}>
        <FormattedMessage id="cns.rule.label" />{' '}
        "{rule.name}"
      </Typography>
      <div style={{ paddingLeft: '76rem' }}>
      <ClosingButton onClick={conditionAborter(abortCondition)} />
      </div>
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
      <StyledFieldSeparator />
      <div style={{ paddingLeft: '76rem' }}>
     <NextButton onClick={conditionFinisher(finishCondition)}/> 
      </div>
    </StyledRuleDetailCondition>
  )
}

export default RuleDetailCondition
