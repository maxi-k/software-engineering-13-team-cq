import React from 'react'
import { PredicateCounterValue } from '@/model/Rule'
import { FormattedMessage } from 'react-intl'

export interface PredicateCounterProps {
  // options for the select field
  // e.g All, Any, None
  options: PredicateCounterValue[]
  beforeText: string
  afterText: string
}

const PredicateCounter: React.SFC<PredicateCounterProps> = ({ options, beforeText, afterText }) => {
  return (
    <p>
      <FormattedMessage id={beforeText} />
      <select>
        {options.map((predicateCounterOption: PredicateCounterValue) => (
          <option> {predicateCounterOption} </option> // e.g. All, One, None
        ))}
      </select>
      <FormattedMessage id={afterText} />
    </p>
  )
}

export default PredicateCounter
