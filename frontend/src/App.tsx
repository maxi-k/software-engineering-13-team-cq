import React, { Component } from 'react';
import './App.css';

import {
  ContentDiv,
  MainDiv,
  PageContainer
} from '@fleetdata/shared/styled-components/page.style';

import IntlWrapper from '@/IntlWrapper'
import StoreWrapper from '@/StoreWrapper'
import StyleWrapper from '@/StyleWrapper'

import Pages from '@/pages'
import Header from '@/organisms/Header'

const AppWrapper: React.SFC<JSX.IntrinsicAttributes> = ({ children }) => (
  <StyleWrapper>
    <StoreWrapper>
      <IntlWrapper>
        {children}
      </IntlWrapper>
    </StoreWrapper>
  </StyleWrapper>
)

class App extends Component {
  public render() {
    return (
      <AppWrapper>
        <MainDiv>
          <Header />
          <ContentDiv>
            <PageContainer>
              <Pages />
            </PageContainer>
          </ContentDiv>
        </MainDiv>
      </AppWrapper>
    );
  }
}

export default App;
