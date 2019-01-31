import * as React from 'react';

import { rem } from 'polished';
import { FormattedMessage } from 'react-intl';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ContentDiv } from '../styled-components/page.style';
import { fontS, footergray, white } from '../styles/variables';

export class Footer extends React.Component<React.Props<HTMLElement>> {
  public render() {
    return (
      <StyledFooterWraper>
        <StyledFooterContainer>
          <div>
            <StyledFooterLink to="/imprint">
              <FormattedMessage id="footer.link.imprint" />
            </StyledFooterLink>
          </div>
          <div>
            <StyledFooterLink to="/privacy">
              <FormattedMessage id="footer.link.dataProtection" />
            </StyledFooterLink>
          </div>
          <div>
            <StyledFooterLink to="/cookies">
              <FormattedMessage id="footer.link.cookies" />
            </StyledFooterLink>
          </div>
          <div>
            <StyledFooterLink to="/t-and-c">
              <FormattedMessage id="footer.link.termsAndConditions" />
            </StyledFooterLink>
          </div>
          <div style={{ marginLeft: 'auto' }}>
            <FormattedMessage id="footer.link.copyright" />
          </div>
        </StyledFooterContainer>
      </StyledFooterWraper>
    );
  }
}

export const StyledFooterContainer = styled(ContentDiv)`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  font-size: ${fontS};
  margin: 0;

  & div {
    color: ${white};
  }
`;

export const StyledFooterLink = styled(Link)`
  color: ${white};
  text-decoration: none;
  &:hover,
  &:focus,
  &:visited {
    text-decoration: underline;
  }
  margin-right: ${rem(60)};
`;

const StyledFooterWraper = styled.footer`
  display: flex;
  width: 100%;
  justify-content: space-around;
  background-color: ${footergray};
  bottom: 0;
  position: absolute;
  height: ${rem(38)};
  z-index: 999;
`;
