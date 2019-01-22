import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'

import { FetchingAttributes, NotificationRuleDetail } from '@/model'

// import Typography from '@material-ui/core/Typography'

import ErrorMessage from '@/atoms/ErrorMessage'
// import LoadingIndicator from '@/atoms/LoadingIndicator'
// import RuleRecipientTag from '@/atoms/RuleRecipientTag'
// import FieldDescriptor from '@/molecules/FieldDescriptor'
// import RuleIcon from '@/icons/RuleIcon'
import NextButton from '@/atoms/NextButton'
import ClosingButton from '@/atoms/ClosingButton'

export type FinishGeneralType = (event: React.SyntheticEvent<any, any>) => void
export type AbortGeneralType = (event: React.SyntheticEvent<any, any>) => void

export interface RuleDetailGeneralAttributes {
  ruleDetail: NotificationRuleDetail
  finishGeneral: FinishGeneralType
  abortGeneral: AbortGeneralType
}

export type RuleDetailGeneralProps = FetchingAttributes
  & RuleDetailGeneralAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetailGeneral = styled.div`
`
/*const StyledRuleInformation = styled.div`
    display: flex;
    flex-direction: row;
`*/

/*const StyledInfoBlock = styled.div`
    flex-basis: 50%;
    flex-shrink: 1;
    padding: 1rem;
`*/

const StyledFieldSeparator = styled.div`
    padding: 1rem;
`
const generalFinisher = (finishGeneral: FinishGeneralType) =>
  (event: React.SyntheticEvent<any, any>): void =>
    finishGeneral(event)

const generalAborter = (abortGeneral: AbortGeneralType) =>
  (event: React.SyntheticEvent<any, any>): void =>
    abortGeneral(event)

const RuleDetailGeneral: React.SFC<RuleDetailGeneralProps> = ({
  isFetching, hasFetchError, ruleDetail, finishGeneral, abortGeneral, ...props
}) => {

  if (hasFetchError) {
    return (
      <ErrorMessage message={
        <FormattedMessage id="cns.message.fetch.error" />
      } />
    )
  }
  /*if (isFetching || typeof ruleDetail === 'undefined' || ruleDetail === null) {
    return <LoadingIndicator isCentral={true} />
  }*/

  return (
    <StyledRuleDetailGeneral {...props}>
      
      <div style={{ paddingLeft: '96rem' }}>
        <ClosingButton onClick={generalAborter(abortGeneral)} />
      </div>
      <StyledFieldSeparator />
      
      <StyledFieldSeparator />
      <div style={{ paddingLeft: '80rem' }}>
        <NextButton onClick={generalFinisher(finishGeneral)} />
      </div>
    </StyledRuleDetailGeneral>
  )
}

export default RuleDetailGeneral
