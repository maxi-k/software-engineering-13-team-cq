import React from 'react'
import Select from 'react-select';
import { FormattedMessage } from 'react-intl'
import { SelectFormattedValue } from '@/model';

export interface PredicateCounterProps {
  value: SelectFormattedValue
  options: SelectFormattedValue[]
  beforeText: string
  afterText: string
}

const PredicateCounter: React.SFC<PredicateCounterProps> = ({ value, options, beforeText, afterText}) => { 
  return (
    <p>
      <FormattedMessage id={beforeText} />
      <Select value = {value} options = {options} />
      <FormattedMessage id={afterText} />
    </p>
  )
}

export default PredicateCounter
