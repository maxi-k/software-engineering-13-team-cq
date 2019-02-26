import React from 'react'
import { FormattedMessage } from 'react-intl'
import { connect } from 'react-redux'

import { StateMapper } from '@/state/connector'
import { PredicateFieldListState } from '@/state/predicate-field'
import { predicateFieldListSelector } from '@/state/selectors'

import { VehiclePredicateFields, PredicateField } from '@/model'
import { capitalizeString } from '@/utilities/string-util';

interface PredicateFieldUnitAttributes {
  providerName: string,
  fieldName: string
}

interface StateAttributes {
  predicateFields: PredicateFieldListState
}

export type PredicateFieldUnitProps = PredicateFieldUnitAttributes
  & StateAttributes
  & React.HTMLAttributes<HTMLDivElement>

const formattingString = (datatype: string) => `cns.predicate.datatype.${datatype.toLowerCase()}.unit`

const PredicateFieldUnit: React.SFC<PredicateFieldUnitProps> = ({
  providerName, fieldName, predicateFields
}) => {
  const vehicleFieldName = capitalizeString(providerName)
  const possibleFields: VehiclePredicateFields | undefined = (
    vehicleFieldName in predicateFields
      ? predicateFields[vehicleFieldName as keyof typeof predicateFields]
      : undefined
  )
  if (typeof possibleFields === 'undefined' || possibleFields === null) {
    return <FormattedMessage id={formattingString('undefined')} />
  }

  const correctField: PredicateField<any> | undefined = possibleFields.predicateFields.find((predicateField) => (
    predicateField.fieldName === fieldName
  ))
  if (typeof correctField === 'undefined' || correctField === null) {
    return <FormattedMessage id={formattingString('undefined')} />
  }

  return (
    <FormattedMessage id={formattingString(correctField.dataType)} />
  )
}

const mapStateToProps: StateMapper<{}, StateAttributes> = (state, ownProps) => ({
  predicateFields: predicateFieldListSelector(state)
})

export default connect(mapStateToProps)(PredicateFieldUnit)
