import {
  createGenerateClassName,
  createMuiTheme,
  jssPreset,
  MuiThemeProvider,
} from '@material-ui/core/styles';
import { create } from 'jss';
import * as React from 'react';
import { addLocaleData, FormattedMessage, IntlProvider } from 'react-intl';
import { JssProvider } from 'react-jss';
import { BrowserRouter as Router } from 'react-router-dom';

import { DialogContent, Grid, ListItemText } from '@material-ui/core';
import { rem } from 'polished';
import de from 'react-intl/locale-data/de';
import en from 'react-intl/locale-data/en';
import styled from 'styled-components';
import { messages } from '@/i18n';
import { AddButton } from './shared/components/actions/actions.styles';
import { BMWButton } from './shared/components/button';
import { Footer } from './shared/components/footer';
import { Header } from './shared/components/header/header.component';
import AddIcon from './shared/components/icons/add.icon';
import {
  StyledDialog,
  StyledDialogActions,
  StyledDialogTitle,
  StyledFleetSelect,
  StyledFormControl,
  StyledFormError,
  StyledInput,
  StyledMenuItem,
  StyledSelect,
} from './shared/styled-components/form/form-elements.style';
import {
  Container,
  InnerContainer,
  StyledSelectorContainer,
} from './shared/styled-components/navigation.style';
import {
  ContentDiv,
  MainDiv,
  PageContainer,
  StyledTopPaddingContainer,
} from './shared/styled-components/page.style';
import { defaultTheme } from './shared/styles/theme';
import { lightblue } from './shared/styles/variables';

export const MAIN_WRAPPER_ID = 'content-wrapper';

addLocaleData([...en, ...de]);

const generateClassName = createGenerateClassName();

const StyledDivider = styled.div`
    min-height: ${rem(36)};
`;

class App extends React.Component<
  object,
  { open: boolean; language: 'en' | 'de' }
  > {
  public state: { language: 'en' | 'de'; open: boolean };
  private jss = create(jssPreset());
  private theme = createMuiTheme(defaultTheme);

  constructor(props: { [key: string]: unknown }) {
    super(props);
    this.jss.setup({
      insertionPoint:
        document.getElementById('jss-insertion-point') || undefined,
    });
    this.state = { language: 'en', open: false };
  }

  public componentDidMount() {
    // breaks jest test because it's not implemented
    // in jsdom
    // window.scrollTo({ top: 0 });
  }

  public switchLanguage = (language: 'en' | 'de') => {
    this.setState(prevState => ({ ...prevState, language }));
  };

  public fakeFunction = () => {
    return null;
  };

  public toggleDialog = () =>
    this.setState(prevState => ({ ...prevState, open: !prevState.open }));

  public render() {
    return (
      <JssProvider jss={this.jss} generateClassName={generateClassName}>
        <MuiThemeProvider theme={this.theme}>
          <IntlProvider
            locale={this.state.language}
            textComponent={React.Fragment}
            messages={messages[this.state.language]}
          >
            <Router>
              <>
                <MainDiv id={MAIN_WRAPPER_ID}>
                  <Header
                    isLoggedIn={true}
                    switchLanguage={this.switchLanguage}
                    language={this.state.language}
                  />
                  <ContentDiv>
                    <PageContainer>
                      <Grid container spacing={40}>
                        <Grid item xs={12} md={3} lg={2}>
                          <StyledTopPaddingContainer>
                            Your content goes here...
                            <StyledDivider />
                            <BMWButton
                              onClick={this.toggleDialog}
                              primary="true"
                              width="auto"
                              label="Open Dialog"
                              icon={<img src="/assets/svg/chevron-right.svg" />}
                              autoFocus
                            />
                            <StyledDivider />
                            <StyledSelectorContainer>
                              <Container elevation={0}>
                                <InnerContainer>
                                  <div className="nav-label">Nav Button</div>
                                </InnerContainer>
                              </Container>
                            </StyledSelectorContainer>
                            <StyledDivider />
                            <AddButton
                              onClick={this.fakeFunction}
                              variant="outlined"
                              classes={{ label: 'label' }}
                            >
                              <AddIcon
                                color={lightblue}
                                height={40}
                                width={40}
                              />
                              <span>
                                <FormattedMessage id="vehicle.add.vehicles.add" />
                              </span>
                            </AddButton>
                            <StyledDivider />
                            <StyledDialog
                              open={this.state.open}
                              // maxWidth="md"
                              scroll="paper"
                              classes={{ paper: 'paper' }}
                              onBackdropClick={this.toggleDialog}
                            >
                              <StyledDialogTitle>Hello</StyledDialogTitle>
                              <DialogContent>
                                <StyledFormControl>
                                  <StyledInput
                                    placeholder="Placeholder"
                                    name="contractStartDate"
                                    // onBlur={handleBlur}
                                    // onChange={handleChange}
                                    // value={contractStartDate}
                                    className={true ? '' : 'has-error'}
                                  />
                                  {false && (
                                    <StyledFormError>
                                      <FormattedMessage id="idOfError" />
                                    </StyledFormError>
                                  )}
                                </StyledFormControl>
                              </DialogContent>
                              <StyledDialogActions>
                                <BMWButton
                                  onClick={this.toggleDialog}
                                  primary="true"
                                  width="auto"
                                  label="CLOSE"
                                  autoFocus
                                />
                              </StyledDialogActions>
                            </StyledDialog>
                            <StyledFormControl>
                              <StyledSelect
                                onClick={this.fakeFunction}
                                classes={{
                                  select: 'select',
                                  root: 'root',
                                  icon: 'icon',
                                }}
                                inputProps={{
                                  name: 'fleet-select',
                                  id: 'fleet-select',
                                }}
                                multiple={false}
                                displayEmpty={true}
                                value='ALL_FLEETS'
                              >
                                <StyledMenuItem value={'ALL_FLEETS'}>
                                  <ListItemText>
                                    <FormattedMessage id="vehicle.serviceView.fleetSelection.all" />
                                  </ListItemText>
                                </StyledMenuItem>
                                <StyledMenuItem value={'ALL_FLEETS1'}>
                                  <ListItemText>
                                    <FormattedMessage id="vehicle.serviceView.fleetSelection.all" />
                                  </ListItemText>
                                </StyledMenuItem>
                                <StyledMenuItem value={'ALL_FLEETS2'}>
                                  <ListItemText>
                                    <FormattedMessage id="vehicle.serviceView.fleetSelection.all" />
                                  </ListItemText>
                                </StyledMenuItem>
                              </StyledSelect>
                            </StyledFormControl>
                            <StyledDivider />
                            <StyledFormControl>
                              <StyledFleetSelect
                                onClick={this.fakeFunction}
                                isNavigation={true}
                                classes={{
                                  select: 'select',
                                  root: 'root',
                                  icon: 'icon',
                                }}
                                inputProps={{
                                  name: 'fleet-select',
                                  id: 'fleet-select',
                                }}
                                multiple={false}
                                displayEmpty={true}
                                value='ALL_FLEETS'
                              >
                                <StyledMenuItem value={'ALL_FLEETS'}>
                                  <ListItemText>
                                    <FormattedMessage id="vehicle.serviceView.fleetSelection.all" />
                                  </ListItemText>
                                </StyledMenuItem>
                                <StyledMenuItem value={'ALL_FLEETS1'}>
                                  <ListItemText>
                                    <FormattedMessage id="vehicle.serviceView.fleetSelection.all" />
                                  </ListItemText>
                                </StyledMenuItem>
                                <StyledMenuItem value={'ALL_FLEETS2'}>
                                  <ListItemText>
                                    <FormattedMessage id="vehicle.serviceView.fleetSelection.all" />
                                  </ListItemText>
                                </StyledMenuItem>
                              </StyledFleetSelect>
                            </StyledFormControl>
                          </StyledTopPaddingContainer>
                        </Grid>
                      </Grid>
                    </PageContainer>
                  </ContentDiv>
                </MainDiv>
                <Footer />
              </>
            </Router>
          </IntlProvider>
        </MuiThemeProvider>
      </JssProvider>
    );
  }
}

export default App;
