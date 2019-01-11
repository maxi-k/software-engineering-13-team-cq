import React from 'react'
import { ComparisonType } from '@/model/Rule'
import { SelectValue } from '@/model'

export interface ConditionSelectorProps {
  dataTypes: SelectValue[]
  comparisonTypes: ComparisonType[]
}

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ dataTypes, comparisonTypes }) => {
  return (
    <p>
      The
      <select>
        {dataTypes.map((dataType: SelectValue) => (
          <option> {dataType.value} </option>
        ))}
      </select>
      is
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
