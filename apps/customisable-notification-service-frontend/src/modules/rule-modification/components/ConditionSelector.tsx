import React from 'react'
import styled from 'styled-components'
import Select from 'react-select';
import { FormattedMessage } from 'react-intl'

import { SelectValue, SelectFormattedValue, SelectGroupedOptions, SelectOnChangeType } from '@/model'
import { translateSelectValue } from '@/services/translation-service'

import IconButton from '@material-ui/core/IconButton'
import CloseIcon from '@fleetdata/shared/components/icons/close.icon'
import SelectWrapper from '@/modules/shared/components/TextSelectWrapper'

export interface ConditionSelectorProps {
  beforeText: string
  afterText: string
  unit: React.ReactNode

  dataTypeOptions: Array<SelectGroupedOptions<SelectValue>>
  dataTypeValue: SelectValue | null

  comparisonTypeOptions: SelectValue[]
  comparisonTypeValue: SelectValue | null

  comparisonConstant: string,

  onChangeDataType: SelectOnChangeType<SelectFormattedValue | null>
  onChangeComparisonType: SelectOnChangeType<SelectFormattedValue | null>,
  onChangeComparisonConstant: (event: React.FormEvent<HTMLInputElement>) => void,
  onClickRemove(event: React.SyntheticEvent<any, any>): void
}

const StyledSeparator = styled.div`
  width: 4px;
  display: inline-block;
`

const StyledTextInput = styled.input`
  width: 200px;
  height: 35px;
  margin-right: 1rem;
`

const StyledRemovalButton = styled(IconButton)`
  display: inline-block;
`

const RemovalButton: React.SFC<React.HTMLAttributes<HTMLButtonElement>> = ({ onClick }) => (
  <StyledRemovalButton
    onClick={onClick}>
    <CloseIcon width={30} height={30} />
  </StyledRemovalButton>
)

const ConditionSelector: React.SFC<ConditionSelectorProps> = (
  { beforeText, afterText, unit,
    dataTypeOptions, dataTypeValue,
    comparisonTypeOptions, comparisonTypeValue, comparisonConstant,
    onChangeDataType, onChangeComparisonType, onChangeComparisonConstant,
    onClickRemove,
  }
) => {
  return (
    <div>
      <FormattedMessage id={beforeText} />
      <SelectWrapper>
        <Select
          data-test-id="rule-modification-condition-field-selector"
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
      <StyledTextInput
        data-test-id="rule-modification-condition-field-comparison-constant"
        value={comparisonConstant}
        onChange={onChangeComparisonConstant}
        type="text" />
      {unit}
      <RemovalButton onClick={onClickRemove} />
    </div>
  )
}

export default ConditionSelector
