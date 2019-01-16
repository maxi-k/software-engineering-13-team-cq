import React from 'react'
import { ComparisonType, VehicleDataType } from '@/model/Rule'

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string
  dataTypes: VehicleDataType[]
  comparisonTypes: ComparisonType[]
}

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ beforeText, afterText, dataTypes, comparisonTypes }) => {
  return (
    <p>
      <select>
        {comparisonTypes.map((comparisonType: ComparisonType) => (
          <option> {comparisonType} </option>
        ))}
      </select>
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
