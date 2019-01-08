import React from 'react'
import { connect, StateType, StateMapper, DispatchMapper } from '@/state/connector'
import { switchLanguage, LanguageType } from '@/state/language'
import { languageSelector, isAuthenticatedSelector } from '@/state/selectors'

import { Header as BMWHeader } from '@fleetdata/shared/components/header/header.component'

export interface HeaderAttributes { }

export interface StateAttributes {
  // from bmw header props
  isLoggedIn: boolean,
  language: LanguageType
}
export interface DispatchAttributes {
  // from bmw header props
  switchLanguage: ((language: LanguageType) => void)
}

export type HeaderProps = HeaderAttributes
  & StateAttributes
  & DispatchAttributes

const mapStateToProps: StateMapper<HeaderAttributes, StateAttributes> = (state, props) => ({
  language: languageSelector(state),
  isLoggedIn: isAuthenticatedSelector(state)
})

const mapDispatchToProps: DispatchMapper<HeaderAttributes, DispatchAttributes> = (dispatch, props) => ({
  switchLanguage: (lang: LanguageType) => dispatch(switchLanguage(lang))
})

const Header: React.SFC<HeaderProps> = (headerProps) => (
  <BMWHeader {...headerProps} />
)

export default connect<StateAttributes, DispatchAttributes, HeaderAttributes, StateType>(
  mapStateToProps,
  mapDispatchToProps)(Header)
