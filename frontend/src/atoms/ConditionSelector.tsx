import React from 'react'
import { VehicleDataType, ComparisonType } from '@/model/Rule'

export interface ConditionSelectorProps {
  dataTypes: VehicleDataType[],
  comparisonTypes: ComparisonType[]
}

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ dataTypes, comparisonTypes }) => { 
  return (
    <p>
      The   
      <select>
        {dataTypes.map((dataType: VehicleDataType) => (
          <option> {dataType} </option>
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

// @ts-ignore
export default ConditionSelector