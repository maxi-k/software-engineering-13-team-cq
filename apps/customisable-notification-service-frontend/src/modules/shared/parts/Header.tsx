import React from 'react'
import { connect, StateType, StateMapper, DispatchMapper } from '@/state/connector'
import { isAuthenticatedSelector } from '@/state/selectors'

import { Header as BMWHeader } from '@fleetdata/shared/components/header/header.component'

export interface HeaderAttributes {
  language: 'en' | 'de',
  switchLanguage(lang: 'en' | 'de'): void
}

export interface StateAttributes {
  isLoggedIn: boolean
}
export interface DispatchAttributes {
}

export type HeaderProps = HeaderAttributes
  & StateAttributes
  & DispatchAttributes

const mapStateToProps: StateMapper<HeaderAttributes, StateAttributes> = (state, props) => ({
  isLoggedIn: isAuthenticatedSelector(state)
})

const mapDispatchToProps: DispatchMapper<HeaderAttributes, DispatchAttributes> = (dispatch, props) => ({
})

const Header: React.SFC<HeaderProps> = (headerProps) => (
  <BMWHeader {...headerProps} />
)

export default connect<StateAttributes, DispatchAttributes, HeaderAttributes, StateType>(
  mapStateToProps,
  mapDispatchToProps)(Header)
