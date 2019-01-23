import React from 'react'
import styled from 'styled-components'
import Select from 'react-select';
import { SelectFormattedValue } from '@/model'
import { FormattedMessage } from 'react-intl'

import SelectWrapper from '@/atoms/TextSelectWrapper'

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string

  dataTypeOptions: SelectFormattedValue[]
  dataTypeValue: SelectFormattedValue

  comparisonTypeOptions: SelectFormattedValue[]
  comparisonTypeValue: SelectFormattedValue
}

const StyledSeparator = styled.div`
  width: 4px;
  display: inline-block;
`

const StyledTextInput = styled.input`
  width: 200px;
  height: 35px;
`

const ConditionSelector: React.SFC<ConditionSelectorProps> = (
  { beforeText, afterText,
    dataTypeOptions, dataTypeValue,
    comparisonTypeOptions, comparisonTypeValue }
) => {
  return (
    <p>
      <FormattedMessage id={beforeText} />
      <SelectWrapper>
        <Select value={dataTypeValue} options={dataTypeOptions} />
      </SelectWrapper>
      <FormattedMessage id={afterText} />
      <SelectWrapper>
        <Select value={comparisonTypeValue} options={comparisonTypeOptions} />
      </SelectWrapper>
      <StyledSeparator />
      <StyledTextInput type="text" />
      %.
    </p>
  )
}

export default ConditionSelector
