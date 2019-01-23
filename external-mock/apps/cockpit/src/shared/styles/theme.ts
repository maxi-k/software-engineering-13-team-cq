import { ThemeOptions } from '@material-ui/core/styles/createMuiTheme';

import { darkgray, lightblue } from './variables';

const BMWFont = [
  'BMWGroupCondensed',
  'Arial Condensed',
  'Arial',
  'Helvetica',
  'sans-serif',
].join(',');

export const defaultTheme: ThemeOptions = {
  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 1025,
      lg: 1281,
      xl: 1921,
    },
  },
  typography: {
    useNextVariants: true,
    fontFamily: BMWFont,
    fontSize: 16,
    body2: {
      color: darkgray,
    },
    button: {
      fontFamily: BMWFont,
    },
    h5: {
      fontFamily: BMWFont,
      fontWeight: 'bold',
    },
  },
  palette: {
    secondary: {
      main: lightblue,
    },
  },
  props: {
    MuiButtonBase: {
      disableRipple: true,
    },
    MuiPaper: {
      elevation: 24,
      square: true,
    },
    MuiInput: {
      disableUnderline: true,
    },
    MuiDialog: {
      disableBackdropClick: true,
    },
  },
};
