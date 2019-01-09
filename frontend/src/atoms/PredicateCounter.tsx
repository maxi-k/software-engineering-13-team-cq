import React from 'react'
import Select from 'react-select'
import { withTheme, WithTheme } from '@material-ui/core/styles'
import { Props as SelectProps } from 'react-select/lib/Select'
import { ValueType, ActionMeta, InputActionMeta } from 'react-select/lib/types'
import { StylesConfig, mergeStyles } from 'react-select/lib/styles'

import { SelectValue } from '@/model'

type OnChangeType = (value: ValueType<SelectValue>, action: ActionMeta | InputActionMeta) => void
export interface PredicateCounterAttributes {
  options: SelectValue[],
  onChange: OnChangeType,
  styles?(theme: WithTheme): StylesConfig,
}
export type PredicateCounterProps = PredicateCounterAttributes & Partial<SelectProps<SelectValue>>

const defaultStylesProp = (_: WithTheme) => ({})
const defaultStyles = (theme: WithTheme) => ({
  menu: (base: React.CSSProperties) => ({
    ...base,
    borderRadius: 0,
  }),
  option: (base: React.CSSProperties, state: any) => ({
    ...base,
    padding: "0.4em",
    // have to ignore typescript annotations because MUI theme.palette is untyped.
    // @ts-ignore
    backgroundColor: state.isFocused ? theme.palette.grey[300] : base.backgroundColor
  }),
  control: (base: React.CSSProperties) => ({
    ...base,
    borderRadius: 0,
    // @ts-ignore
    borderColor: theme.palette.secondary.main,
  })
})

const PredicateCounter: React.SFC<PredicateCounterProps> = ({
  styles = defaultStylesProp, theme, ...otherProps
}) => {
  // mergeStyles actually has the wrong type signature...
  // Guess how long it took to find that bug... 😩
  // TODO: Create issue in type repo @types/react-select
  // @ts-ignore
  const mergedStyles: StylesConfig = mergeStyles(defaultStyles(theme), styles(theme))
  return (<Select
    {...otherProps}
    styles={mergedStyles}
    closeMenuOnSelect={true}
  />
  )
}

// @ts-ignore
export default withTheme()(PredicateCounter)