import React from 'react'
import { ComparisonType } from '@/model/Rule'
import { SelectValue } from '@/model'

import Select from 'react-select'
import { FormattedMessage } from 'react-intl'

export interface ConditionSelectorProps {
  dataTypes: SelectValue[]
  comparisonTypes: ComparisonType[]
}

const ConditionSelector: React.SFC<ConditionSelectorProps> = ({ dataTypes, comparisonTypes }) => {
  return (
    <p>
      The
      <Select
        //value={selectedOption}
        //onChange={this.handleChange}
        options={dataTypes}
      />
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
