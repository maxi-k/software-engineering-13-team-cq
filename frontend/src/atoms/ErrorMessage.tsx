import React from 'react'
import { withTheme, WithTheme } from '@material-ui/core/styles'
import { Theme } from '@material-ui/core/styles/createMuiTheme'

import CheckCircleIcon from '@material-ui/icons/CheckCircle'
import ErrorIcon from '@material-ui/icons/Error'
import InfoIcon from '@material-ui/icons/Info'
import CloseIcon from '@material-ui/icons/Close'
import green from '@material-ui/core/colors/green'
import amber from '@material-ui/core/colors/amber'
import IconButton from '@material-ui/core/IconButton'
import SnackbarContent from '@material-ui/core/SnackbarContent'
import WarningIcon from '@material-ui/icons/Warning'

/*
 * Based on https://material-ui.com/demos/snackbars/
 */

const variantIcon = {
  success: CheckCircleIcon,
  warning: WarningIcon,
  error: ErrorIcon,
  info: InfoIcon,
};

const messageStyles = (theme: Theme) => ({
  success: {
    backgroundColor: green[600],
  },
  error: {
    backgroundColor: theme.palette.error.dark,
  },
  info: {
    backgroundColor: theme.palette.primary.dark,
  },
  warning: {
    backgroundColor: amber[700],
  },
  icon: {
    fontSize: 20,
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing.unit,
  },
  message: {
    display: 'flex',
    alignItems: 'center',
  },
  spacing: {
    margin: theme.spacing.unit,
  }

});

export interface SpecifiedMessageAttributes {
  message: React.ReactNode | string,
}
export type SpecifiedMessageProps = SpecifiedMessageAttributes & React.HTMLAttributes<HTMLDivElement>

interface GenericMessageAttributes extends SpecifiedMessageAttributes, WithTheme {
  className?: string,
  variant: 'success' | 'warning' | 'error' | 'info',
  onClose?(event: React.MouseEvent<HTMLElement, MouseEvent>): void
};
type GenericMessageProps = GenericMessageAttributes & React.HTMLAttributes<HTMLDivElement>

const GenericMessage: React.SFC<GenericMessageProps> = (props) => {
  const { className, message, onClose, variant, ...other } = props;
  const Icon = variantIcon[variant];
  const classes = messageStyles(props.theme)

  return (
    <SnackbarContent
      style={{
        backgroundColor: classes[variant].backgroundColor
      }}
      aria-describedby="client-snackbar"
      message={
        <span id="client-snackbar" style={classes.message}>
          <Icon style={{ ...classes.icon, ...classes.iconVariant }} />
          {message}
        </span>
      }
      action={[
        <IconButton
          key="close"
          aria-label="Close"
          color="inherit"
          onClick={onClose}
        >
          <CloseIcon style={classes.icon} />
        </IconButton>,
      ]}
      {...other}
    />
  );
}

const StyledGenericMessage = withTheme()(GenericMessage)

const ErrorMessage: React.SFC<SpecifiedMessageProps> = (props) => (
  <StyledGenericMessage
    {...props}
    variant="error" />
)

export default ErrorMessage
