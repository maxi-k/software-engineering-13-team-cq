import React from 'react'
import styled from 'styled-components'
import { FormattedMessage } from 'react-intl'
import FleetSelector, { FleetSelectorProps } from '@/atoms/FleetSelector'

import {
  FetchingAttributes,
  NotificationRuleDetail,
} from '@/model'

import ErrorMessage from '@/atoms/ErrorMessage'
// import LoadingIndicator from '@/atoms/LoadingIndicator'
import NextButton from '@/atoms/NextButton'
import ClosingButton from '@/atoms/ClosingButton'

export type FinishVehiclesType = (event: React.SyntheticEvent<any, any>) => void
export type AbortVehiclesType = (event: React.SyntheticEvent<any, any>) => void

export interface RuleDetailVehiclesAttributes {
  rule: NotificationRuleDetail
  finishVehicles: FinishVehiclesType
  abortVehicles: AbortVehiclesType
}

export type RuleDetailVehiclesProps = FetchingAttributes
  & RuleDetailVehiclesAttributes
  & React.HTMLAttributes<HTMLDivElement>

const StyledRuleDetailVehicles = styled.div`
`

const StyledFieldSeparator = styled.div`
    padding: 1rem;
`

/*const TextElementWrapper = styled.div`
    paddingLeft: 1rem;
`*/

const vehiclesFinisher = (finishVehicles: FinishVehiclesType) =>
  (event: React.SyntheticEvent<any, any>): void =>
    finishVehicles(event)

const vehiclesAborter = (abortVehicles: AbortVehiclesType) =>
  (event: React.SyntheticEvent<any, any>): void =>
    abortVehicles(event)

const RuleDetailVehicles: React.SFC<RuleDetailVehiclesProps> = ({
  isFetching, hasFetchError, rule, finishVehicles, abortVehicles, ...props
}) => {

  if (hasFetchError) {
    return (
      <ErrorMessage message={
        <FormattedMessage id="cns.message.fetch.error" />
      } />
    )
  }
  /* if (isFetching || typeof rule === 'undefined' || rule === null) {
    return <LoadingIndicator isCentral={true} />
  }  */

const fleetSelectorProps: Partial<FleetSelectorProps> = {
  value: { label: 'BMW', value: 'BMW' },
  options: [
    { label: 'BMW', value: 'BMW' },
    { label: 'Audi', value: 'Audi' },
    { label: 'Mercedes', value: 'Mercedes' }
  ],
  styles: (_) => ({
    input: (base) => ({
      ...base,
      width: '500px'
    })
  })
}



  return (
    <StyledRuleDetailVehicles {...props}>
      <div style={{ paddingLeft: '96rem' }}>
        <ClosingButton onClick={vehiclesAborter(abortVehicles)} />
      </div>
      <StyledFieldSeparator />
      <div style={{ paddingLeft: '1rem' }}>
        <FleetSelector {...fleetSelectorProps} />
      </div>
      <StyledFieldSeparator />
      <div style={{ paddingLeft: '80rem' }}>
        <NextButton onClick={vehiclesFinisher(finishVehicles)} />
      </div>
    </StyledRuleDetailVehicles>
  )
}

export default RuleDetailVehicles
