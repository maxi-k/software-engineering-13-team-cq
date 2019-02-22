import React from 'react'
import Select from 'react-select'
import { withTheme, WithTheme } from '@material-ui/core/styles'
import { Props as SelectProps } from 'react-select/lib/Select'
import { SelectComponents } from 'react-select/lib/components'
import { StylesConfig, mergeStyles } from 'react-select/lib/styles'

import { SelectValue, SelectOnChangeType } from '@/model'
import { defaultSelectStyles, defaultSelectStylesProp } from '@/utilities/style-util'

export interface FleetSelectorAttributes {
  value: SelectValue,
  options: SelectValue[],
  placeholder?: string,
  components?: Partial<SelectComponents<any>>,
  theme: WithTheme,
  onChange: SelectOnChangeType<SelectValue>,
  styles?(theme: WithTheme): StylesConfig,
}
export type FleetSelectorProps = FleetSelectorAttributes & Partial<SelectProps<SelectValue>>

const FleetSelector: React.SFC<FleetSelectorProps> = ({
  styles = defaultSelectStylesProp, theme, ...otherProps
}) => {
  // mergeStyles actually has the wrong type signature...
  // Guess how long it took to find that bug... 😩
  // TODO: Create issue in type repo @types/react-select
  // @ts-ignore
  const mergedStyles: StylesConfig = mergeStyles(defaultSelectStyles(theme), styles(theme))
  return (<Select
    {...otherProps}
    styles={mergedStyles}
    closeMenuOnSelect={false}
    isMulti
    isClearable
  />
  )
}

// @ts-ignore
export default withTheme()(FleetSelector)
