import React from 'react'
import { ComparisonType } from '@/model/Rule'
import { SelectValue } from '@/model'

import Select from 'react-select'
import { FormattedMessage } from 'react-intl'

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string
  dataTypes: string[]
  comparisonTypes: ComparisonType[]
}

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ beforeText, afterText, dataTypes, comparisonTypes }) => {
  return (
    <p>
      <FormattedMessage id={beforeText}/>
      <Select
        value={<FormattedMessage id={dataTypes[0]} />}
        options={comparisonTypes.map((dataType) => <FormattedMessage id={dataType}/>)}
      />
      <FormattedMessage id={afterText}/>
      <select>
        {comparisonTypes.map((comparisonType: ComparisonType) => (
          <option> {comparisonType} </option>
        ))}
      </select>
      <input type="text" name="value" />
      %.
    </p>
  )
}

export default ConditionSelector
