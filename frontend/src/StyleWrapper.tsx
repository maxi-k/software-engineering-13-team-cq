import React from 'react'
import {
  createGenerateClassName,
  createMuiTheme,
  jssPreset,
  MuiThemeProvider,
} from '@material-ui/core/styles';
import { create } from 'jss';
import { JssProvider } from 'react-jss';

import { defaultTheme } from '@fleetdata/shared/styles/theme';

const jss = create(jssPreset());
const theme = createMuiTheme(defaultTheme);
const generateClassName = createGenerateClassName();

const StyleWrapper: React.SFC<Props> = ({ children }) => (
  <JssProvider jss={jss} generateClassName={generateClassName}>
    <MuiThemeProvider theme={theme}>
      {children}
    </MuiThemeProvider>
  </JssProvider>
)

export default StyleWrapper
