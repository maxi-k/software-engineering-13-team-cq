import React from 'react'
import Creatable from 'react-select/lib/Creatable';

import { withTheme, WithTheme } from '@material-ui/core/styles'
import { Props as SelectProps } from 'react-select/lib/Select'
import { SelectComponents } from 'react-select/lib/components'
import { StylesConfig, mergeStyles } from 'react-select/lib/styles'

import { SelectValue, SelectOnChangeType } from '@/model'
import { defaultSelectStyles, defaultSelectStylesProp } from '@/utilities/style-util'

export interface RecipientSelectorAttributes {
  value: SelectValue,
  options: SelectValue[],
  placeholder?: string,
  components?: Partial<SelectComponents<any>>,
  theme: WithTheme,
  onChange: SelectOnChangeType<SelectValue>,
  styles?(theme: WithTheme): StylesConfig,
}

type RecipientSelectorProps = RecipientSelectorAttributes & Partial<SelectProps<SelectValue>>

const RecipientSelector: React.SFC<RecipientSelectorProps> = (
  { styles = defaultSelectStylesProp, theme, ...otherProps }
) => {
  // mergeStyles actually has the wrong type signature in the package...
  // @ts-ignore
  const mergedStyles: StylesConfig = mergeStyles(defaultSelectStyles(theme), styles(theme))
  return (<Creatable
    {...otherProps}
    styles={mergedStyles}
    closeMenuOnSelect={false}
    isMulti
    isClearable />
  )
}

// mergeStyles actually has the wrong type signature in the package...
// @ts-ignore
export default withTheme()(RecipientSelector)
