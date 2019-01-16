import React from 'react'
import TextField, { TextFieldProps } from '@material-ui/core/TextField';
import { FormattedMessage } from 'react-intl'

export interface InputFieldAttributes {
  label: string,
  onChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement>,
  value: string
}
export type InputFieldProps = TextFieldProps & InputFieldAttributes

const InputField: React.SFC<InputFieldProps> = (
  { variant, fullWidth = true, margin, label, ...props }
) => (
    <TextField
      variant="filled"
      fullWidth={fullWidth}
      margin={margin || 'normal'}
      label={<FormattedMessage id={label} />}
      {...props}
    />
  )

export default InputField
