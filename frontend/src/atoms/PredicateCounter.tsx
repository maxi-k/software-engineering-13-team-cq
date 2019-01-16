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
      <div style={{ width : '200px', height : '50px', display: 'inline-block'}}>
        <Select value = {value} options = {options} />
      </div>
      <FormattedMessage id={afterText} />
    </p>
  )
}

export default PredicateCounter
