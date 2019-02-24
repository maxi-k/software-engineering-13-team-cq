import React from 'react';
import './App.css';

import {
  ContentDiv,
  MainDiv,
  PageContainer,
  StyledTopPaddingContainer
} from '@fleetdata/shared/styled-components/page.style';
import { Footer } from '@/fleetdata/shared/components/footer'

import StoreWrapper from '@/wrappers/StoreWrapper'
import StyleWrapper from '@/wrappers/StyleWrapper'

import Pages from '@/pages'
import Header from '@/modules/shared/parts/Header'

import { addLocaleData, IntlProvider } from 'react-intl';
import de from 'react-intl/locale-data/de';
import en from 'react-intl/locale-data/en';
import { messages } from '@/i18n';

addLocaleData([...en, ...de]);

type LanguageType = 'en' | 'de'
interface AppState {
  language: LanguageType
}

class App extends React.Component<object, AppState> {

  constructor(props: object) {
    super(props)
    this.state = { language: 'en' }
    this.switchLanguage = this.switchLanguage.bind(this)
  }

  public switchLanguage(lang: LanguageType) {
    this.setState({ language: lang })
  }

  public render() {
    return (
      <StyleWrapper>
        <IntlProvider
          locale={this.state.language}
          textComponent={React.Fragment}
          messages={messages[this.state.language]} >
          <StoreWrapper>
            <MainDiv>
              <Header
                language={this.state.language}
                switchLanguage={this.switchLanguage} />
              <ContentDiv style={{ paddingBottom: '2rem' }} >
                <StyledTopPaddingContainer />
                <PageContainer>
                  <Pages />
                </PageContainer>
              </ContentDiv>
              <Footer />
            </MainDiv>
          </StoreWrapper>
        </IntlProvider>
      </StyleWrapper>
    )
  }
}

export default App;
