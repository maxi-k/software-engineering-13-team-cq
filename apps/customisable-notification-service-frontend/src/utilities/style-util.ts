
import { Theme } from '@material-ui/core/styles'

export const defaultSelectStylesProp = (_: Theme) => ({})
export const defaultSelectStyles = (theme: Theme) => ({
  menu: (base: React.CSSProperties) => ({
    ...base,
    borderRadius: 0,
    zIndex: 1000
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
