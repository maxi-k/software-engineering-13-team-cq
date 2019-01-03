import update from 'immutability-helper';
import { rem } from 'polished';
import * as React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
import { fromEvent, Observable, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { LangSwitch } from '@/i18n/components/lang-switch/lang-switch.container.component';
import { lightgray, white } from '@/fleetdata/shared/styles/variables';
import { media, sizes } from '@/fleetdata/utils/media-query';
import styled from 'styled-components';
import { WithRouterAndStateMainNavigation } from '../main-navigation/main-navigation.container.component';
import { User } from './user.container.component';

const StyledHeaderContainer = styled.div`
  z-index: 999;
  position: fixed;
  background-color: white;
  width: 100%;
  top: 0;
  margin-left: auto;
  margin-right: auto;
`;

const HeaderFirstLineContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding: ${rem(29)} ${rem(16)} ${rem(4)} ${rem(16)};
  margin-bottom: 3px;
  position: relative;
  z-index: 3;
  background-color: ${white};
  ${media.sm`
    padding-left: ${rem(36)};
    padding-right: ${rem(36)};

    & img {
      height: 30px;
      margin-right: ${rem(16)} !important;
      padding-left: 0!important;
    }
  `};
`;

const LogoContainer = styled.div`
  display: flex;
`;

const StyledImage = styled.img`
  &:nth-child(1) {
    margin-right: 44px;
    padding-left: 11px;
  }
`;

const StyledBorderBottom = styled.div`
  min-width: 100%;
  border-bottom: 5px solid ${lightgray};
`;

const StyledHeaderInnerContainer = styled.div`
  max-width: ${sizes.xxl - 1}px;
  padding: 0 ${rem(30)};
  margin-left: auto;
  margin-right: auto;
  box-sizing: border-box;
`;

const StyledActionsContainer = styled.div`
  display: flex;
  align-items: center;
`;

interface IState {
  collapsed: boolean;
  scrolledBy: number;
}

interface IProps {
  isLoggedIn: boolean;
  language: 'en' | 'de';
  switchLanguage: ((language: 'en' | 'de') => void);
}

class HeaderComponent extends React.Component<
  React.Props<HTMLElement> & IProps & RouteComponentProps<any>,
  IState
> {
  public scrollEvent$: Observable<Event> | undefined;
  public scrollSubscription = new Subscription();

  public state = {
    collapsed: false,
    scrolledBy: window.scrollY,
  };

  public componentDidMount = () => {
    this.scrollEvent$ = fromEvent(window, 'scroll');
    this.scrollSubscription.add(
      this.scrollEvent$
        .pipe(debounceTime(20))
        .subscribe(() => this.handleScroll()),
    );
  };

  public componentWillUnmount = () => {
    this.scrollSubscription.unsubscribe();
  };

  public handleScroll = () => {
    this.setState(prevState => {
      const collapse =
        prevState.scrolledBy < window.scrollY ||
        (prevState.scrolledBy > window.scrollY && window.scrollY > 150);
      return update(prevState, {
        collapsed: { $set: collapse },
        scrolledBy: { $set: window.scrollY },
      });
    });
  };

  public handleMouseEnter = () => {
    this.setState(prevState => {
      return update(prevState, {
        collapsed: { $set: false },
      });
    });
  };

  public handleMouseLeave = () => {
    this.setState(prevState => {
      const collapse = window.scrollY > 10;
      return update(prevState, {
        collapsed: { $set: collapse },
        scrolledBy: { $set: window.scrollY },
      });
    });
  };

  public render() {
    return (
      <StyledHeaderContainer
        onMouseEnter={this.handleMouseEnter}
        onMouseLeave={this.handleMouseLeave}
      >
        <StyledHeaderInnerContainer>
          <HeaderFirstLineContainer>
            <LogoContainer>
              <StyledImage
                src="/assets/svg/bmw-group.svg"
                alt="BMW Group"
                height="35"
              />
              <StyledImage
                src="/assets/img/bmw_group_logos.png"
                alt="BMW Logo"
                height="35"
              />
            </LogoContainer>
            <StyledActionsContainer>
              <LangSwitch
                locale={this.props.language}
                updateIntl={this.props.switchLanguage}
              />
              <User />
            </StyledActionsContainer>
          </HeaderFirstLineContainer>
          <WithRouterAndStateMainNavigation collapsed={this.state.collapsed} />
        </StyledHeaderInnerContainer>
        <StyledBorderBottom />
      </StyledHeaderContainer>
    );
  }
}

export const Header = withRouter(HeaderComponent);
