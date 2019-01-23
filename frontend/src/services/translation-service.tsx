import React from 'react'
import { FormattedMessage } from 'react-intl'
import { SelectFormattedValue, SelectValue } from '@/model'

type TranslateableSelectValue = SelectFormattedValue | SelectValue
export const translateSelectValue = ({ label, value }: TranslateableSelectValue): TranslateableSelectValue => ({
  value,
  label: (typeof label === 'string'
    ? React.createElement(FormattedMessage, { key: label, id: label })
    : label)
})
