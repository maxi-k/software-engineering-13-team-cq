import React from 'react'
import Select from 'react-select'
import { withTheme, WithTheme } from '@material-ui/core/styles'
import { Props as SelectProps } from 'react-select/lib/Select'
import { SelectComponents } from 'react-select/lib/components'
import { ValueType, ActionMeta, InputActionMeta } from 'react-select/lib/types'
import { StylesConfig, mergeStyles } from 'react-select/lib/styles'

import { SelectValue } from '@/model'

interface OnChangeType {
  (value: ValueType<SelectValue>, action: ActionMeta | InputActionMeta): void
}
export interface FleetSelectorAttributes {
  value: SelectValue,
  options: SelectValue[],
  placeholder?: string,
  components?: Partial<SelectComponents<any>>,
  onChange: OnChangeType,
  styles?(theme: WithTheme): StylesConfig,
  theme: WithTheme
}
export type FleetSelectorProps = FleetSelectorAttributes & Partial<SelectProps<SelectValue>>

const defaultStylesProp = (_: WithTheme) => ({})
const defaultStyles = (theme: WithTheme) => ({
  menu: (base: React.CSSProperties) => ({
    ...base,
    borderRadius: 0,
  }),
  option: (base: React.CSSProperties, state: any) => ({
    ...base,
    padding: "1.0em",
    // have to ignore typescript annotations because MUI theme.palette is untyped.
    // @ts-ignore
    backgroundColor: state.isFocused ? theme.palette.grey[300] : base.backgroundColor
  }),
  multiValueLabel: (base: React.CSSProperties) => ({
    ...base,
    padding: "1.0em",
    paddingLeft: "1.0em"
  }),
  control: (base: React.CSSProperties) => ({
    ...base,
    borderRadius: 0,
    // @ts-ignore
    borderColor: theme.palette.secondary.main,
  }),
  input: (base: React.CSSProperties) => ({
    ...base,
    padding: "1.0rem",
    '& input': {
      font: 'inherit',
    },
  })
})

const FleetSelector: React.SFC<FleetSelectorProps> = ({
  styles = defaultStylesProp, theme, ...otherProps
}) => {
  // mergeStyles actually has the wrong type signature...
  // Guess how long it took to find that bug... 😩
  // TODO: Create issue in type repo @types/react-select
  // @ts-ignore
  let mergedStyles: StylesConfig = mergeStyles(defaultStyles(theme), styles(theme))
  return (<Select
    {...otherProps}
    styles={mergedStyles}
    closeMenuOnSelect={false}
    isMulti
    isClearable
  />
  )
}

//@ts-ignore
export default withTheme()(FleetSelector)
