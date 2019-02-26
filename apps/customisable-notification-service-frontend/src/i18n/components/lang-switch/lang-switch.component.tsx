import * as React from 'react';
import { StyledSwitchBtn } from './lang-switch.style';

export interface IProps {
  locale: 'de' | 'en';
  updateIntl: (locale: 'en' | 'de') => void;
}

export class LangSwitch extends React.Component<IProps, object> {
  public updateLocale = (newLocale: 'en' | 'de') => {
    this.props.updateIntl(newLocale);
  };

  public render() {
    return (
      <div data-test-id="language-switcher">
        <StyledSwitchBtn
          onClick={this.updateLocale.bind(this, 'en')}
          className={this.props.locale === 'en' ? 'active' : ''}
          data-test-id="language-switcher-en"
        >
          EN
        </StyledSwitchBtn>
        <StyledSwitchBtn
          onClick={this.updateLocale.bind(null, 'de')}
          className={this.props.locale === 'de' ? 'active' : ''}
          data-test-id="language-switcher-de"
        >
          DE
        </StyledSwitchBtn>
      </div>
    );
  }
}

// export class LangSwitch extends React.Component<IProps, object> {}
// export const LangSwitch = () => <div />;
