import React from 'react'
import Select from 'react-select';
import { SelectValue } from '@/model';

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string

  dataTypeOptions: SelectValue[]
  //dataTypeValue: VehicleDataType

  comparisonTypeOptions: SelectValue[]
  //comparisonTypeValue: ComparisonType
}



const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ beforeText, afterText, dataTypeOptions, comparisonTypeOptions }) => {
  return (
    <p>
      <Select options = {dataTypeOptions} /> 
      <input type="text" name="value" />
      %.
    </p>
  )
}

export default ConditionSelector
