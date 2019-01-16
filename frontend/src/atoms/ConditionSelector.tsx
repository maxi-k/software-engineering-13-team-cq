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

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ beforeText, afterText, dataTypeOptions, dataTypeValue, comparisonTypeOptions, comparisonTypeValue }) => {
  return (
    <p>
      <FormattedMessage id={beforeText} />
      <div style={{ width: '200px', height: '50px', display: 'inline-block' }}>
        <Select value={dataTypeValue} options={dataTypeOptions} />
      </div>
      <FormattedMessage id={afterText} />
      <div style={{ width: '200px', height: '50px', display: 'inline-block' }}>
        <Select value={comparisonTypeValue} options={comparisonTypeOptions} />
      </div>
      <div style={{ width: '4px', display: 'inline-block' }} />
      <input type="text" style={{ width: '200px', height: '35px' }} /> %.
    </p>
  )
}

export default ConditionSelector
