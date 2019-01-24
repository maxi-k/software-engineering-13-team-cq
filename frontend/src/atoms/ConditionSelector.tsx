import React from 'react'
import styled from 'styled-components'
import Select from 'react-select';
import { FormattedMessage } from 'react-intl'

import { SelectValue, SelectFormattedValue, SelectGroupedOptions, SelectOnChangeType } from '@/model'
import { translateSelectValue } from '@/services/translation-service'

import SelectWrapper from '@/atoms/TextSelectWrapper'

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string

  dataTypeOptions: Array<SelectGroupedOptions<SelectValue>>
  dataTypeValue: SelectValue | null

  comparisonTypeOptions: SelectValue[]
  comparisonTypeValue: SelectValue | null

  onChangeDataType: SelectOnChangeType<SelectFormattedValue | null>
  onChangeComparisonType: SelectOnChangeType<SelectFormattedValue | null>
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
    comparisonTypeOptions, comparisonTypeValue,
    onChangeDataType, onChangeComparisonType
  }
) => {
  return (
    <div>
      <FormattedMessage id={beforeText} />
      <SelectWrapper>
        <Select
          value={dataTypeValue ? translateSelectValue(dataTypeValue) : null}
          onChange={onChangeDataType}
          options={dataTypeOptions.map(({ label, options }) => ({
            label: typeof label === 'string' ? <FormattedMessage id={label} /> : label,
            options: options.map(translateSelectValue)
          }))} />
      </SelectWrapper>
      <FormattedMessage id={afterText} />
      <SelectWrapper>
        <Select
          value={comparisonTypeValue ? translateSelectValue(comparisonTypeValue) : null}
          onChange={onChangeComparisonType}
          options={comparisonTypeOptions.map(translateSelectValue)} />
      </SelectWrapper>
      <StyledSeparator />
      <StyledTextInput type="text" />
      %.
    </div>
  )
}

export default ConditionSelector
