import * as React from 'react';
import { FormattedMessage } from 'react-intl';
import { RouteComponentProps } from 'react-router';

import { black, bmwGreen, lightblue, orange } from '../../styles/variables';
import {
  StyledNavLi,
  StyledNavLink,
  StyledNavUl,
} from './main-navigation.style';

const loginRegEx = /\/(login)/;

interface IProps {
  collapsed: boolean;
  isLoggedIn?: boolean;
}

export class MainNavigation extends React.Component<
  RouteComponentProps<any> & IProps,
  object
> {
  public render() {
    return (
      <StyledNavUl className={this.props.collapsed ? 'collapsed' : ''}>
        {this.props.location &&
        !this.props.location.pathname.match(loginRegEx) ? (
          <React.Fragment>
            <>
              {this.props.isLoggedIn && (
                <React.Fragment>
                  <StyledNavLi>
                    <StyledNavLink
                      to="/dashboard"
                      activeClassName="selected"
                      color={lightblue}
                    >
                      <span>
                        <FormattedMessage id="mainnav.dashboard" />
                      </span>
                    </StyledNavLink>
                  </StyledNavLi>
                  <StyledNavLi>
                    <StyledNavLink
                      to="/service-view"
                      activeClassName="selected"
                      color={orange}
                    >
                      <span>
                        <FormattedMessage id="mainnav.serviceView" />
                      </span>
                    </StyledNavLink>
                  </StyledNavLi>
                  <StyledNavLi>
                    <StyledNavLink
                      to="/car-park"
                      activeClassName="selected"
                      color={bmwGreen}
                    >
                      <span>
                        <FormattedMessage id="mainnav.carPark" />
                      </span>
                    </StyledNavLink>
                  </StyledNavLi>
                </React.Fragment>
              )}
              {!this.props.isLoggedIn && (
                <StyledNavLi>
                  <StyledNavLink
                    to="/login"
                    activeClassName="selected"
                    color={black}
                  >
                    <span>
                      <FormattedMessage id="mainnav.loginFleetData" />
                    </span>
                  </StyledNavLink>
                </StyledNavLi>
              )}
            </>
            )}
          </React.Fragment>
        ) : null}
      </StyledNavUl>
    );
  }
}
