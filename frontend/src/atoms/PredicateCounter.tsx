import React from 'react'
import Select from 'react-select';
import { FormattedMessage } from 'react-intl'
import { SelectFormattedValue, SelectValue, SelectOnChangeType } from '@/model';
import { translateSelectValue } from '@/services/translation-service'

import SelectWrapper from '@/atoms/TextSelectWrapper'

export interface PredicateCounterProps {
  value: SelectValue,
  onChange: SelectOnChangeType<SelectFormattedValue>
  options: SelectValue[]
  beforeText: string
  afterText: string
}

const PredicateCounter: React.SFC<PredicateCounterProps> = ({
  value, options, beforeText, afterText, ...selectProps
}) => {
  return (
    <p>
      <FormattedMessage id={beforeText} />
      <SelectWrapper>
        <Select
          {...selectProps}
          value={translateSelectValue(value)}
          options={options.map(translateSelectValue)} />
      </SelectWrapper>
      <FormattedMessage id={afterText} />
    </p>
  )
}

export default PredicateCounter
