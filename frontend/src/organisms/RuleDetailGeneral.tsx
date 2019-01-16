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

export type FinishGeneralType = (event: React.SyntheticEvent<any, any>) => void
export type AbortGeneralType = (event: React.SyntheticEvent<any, any>) => void

export interface RuleDetailGeneralAttributes {
  rule: NotificationRuleDetail
  finishGeneral: FinishGeneralType
  abortGeneral: AbortGeneralType
}

export type RuleDetailGeneralProps = FetchingAttributes
  & RuleDetailGeneralAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetailGeneral = styled.div`
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
const generalFinisher = (finishGeneral: FinishGeneralType) =>
    (e: React.SyntheticEvent<any, any>): void =>
      finishGeneral(e)

const generalAborter = (abortGeneral: AbortGeneralType) =>
    (e: React.SyntheticEvent<any, any>): void =>
      abortGeneral(e)

const RuleDetailGeneral: React.SFC<RuleDetailGeneralProps> = ({
  isFetching, hasFetchError, rule, finishGeneral, abortGeneral, ...props
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
    <StyledRuleDetailGeneral {...props}>
      <Typography variant="h5" style={{ paddingLeft: '1rem' }}>
        <FormattedMessage id="cns.rule.label" />{' '}
        "{rule.name}"
      </Typography>
      <div style={{ paddingLeft: '76rem' }}>
      <ClosingButton onClick={generalAborter(abortGeneral)} />
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
     <NextButton onClick={generalFinisher(finishGeneral)}/> 
      </div>
    </StyledRuleDetailGeneral>
  )
}

export default RuleDetailGeneral