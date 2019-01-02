import React from 'react'
import { addLocaleData, IntlProvider } from 'react-intl';
import de from 'react-intl/locale-data/de';
import en from 'react-intl/locale-data/en';
import { messages } from '@/i18n';

addLocaleData([...en, ...de]);

// TODO: FIXME: TEMPORARY CONSTANT LANGUAGE
export type Language = 'en' | 'de'
const language: Language = 'en'

const IntlWrapper: React.SFC<JSX.IntrinsicAttributes> = ({ children }) => (
  <IntlProvider
    locale={language}
    textComponent={React.Fragment}
    messages={messages[language]} >
    {children}
  </IntlProvider>
)

export default IntlWrapper
