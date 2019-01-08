import React from 'react'
import { addLocaleData, IntlProvider } from 'react-intl';
import de from 'react-intl/locale-data/de';
import en from 'react-intl/locale-data/en';
import { connect, StateMapper } from '@/state/connector'
import { LanguageType } from '@/state/language'
import { languageSelector } from '@/state/selectors'
import { messages } from '@/i18n';

addLocaleData([...en, ...de]);

// TODO: FIXME: TEMPORARY CONSTANT LANGUAGE
export interface IntlWrapperAttributes { }
export interface StateAttributes {
  language: LanguageType
}
export type IntlWrapperProps = IntlWrapperAttributes & StateAttributes

const IntlWrapper: React.SFC<IntlWrapperProps> = ({ language, children }) => (
  <IntlProvider
    locale={language}
    textComponent={React.Fragment}
    messages={messages[language]} >
    {children}
  </IntlProvider>
)

const mapStateToProps: StateMapper<IntlWrapperAttributes, StateAttributes> = (state) => ({
  language: languageSelector(state)
})

export default connect(mapStateToProps)(IntlWrapper)
