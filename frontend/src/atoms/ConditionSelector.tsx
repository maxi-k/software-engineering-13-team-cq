import React from 'react'
import Select from 'react-select';
import { SelectFormattedValue } from '@/model'
import { FormattedMessage } from 'react-intl'

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string

  dataTypeOptions: SelectFormattedValue[]
  dataTypeValue: SelectFormattedValue

  comparisonTypeOptions: SelectFormattedValue[]
  comparisonTypeValue: SelectFormattedValue
}

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ beforeText, afterText, dataTypeOptions, dataTypeValue, comparisonTypeOptions, comparisonTypeValue}) => { 
  return (
    <p>
      <FormattedMessage id={beforeText} />
      <Select value = {dataTypeValue} options = {dataTypeOptions} />
      <FormattedMessage id={afterText} />
      <Select value = {comparisonTypeValue} options = {comparisonTypeOptions} />
      <input type = "text" /> %.
    </p>
  )
}

export default ConditionSelector
