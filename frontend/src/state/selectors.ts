import { createSelector, Selector as StateSelector } from 'reselect'
import { RootState } from './index'
import { LanguageType, LanguageState } from './language'
import { AuthState } from './auth'
import { RuleState, RuleOverviewState, RuleDetailState } from './rule'

import { RouterState } from 'connected-react-router'
import { Location } from 'history'

export type Selector<T> = StateSelector<RootState, T>

export const languageStateSelector: Selector<LanguageState> = state => state.language
export const languageSelector: Selector<LanguageType> = createSelector(
  [languageStateSelector],
  languageState => languageState.currentLanguage
)

export const authStateSelector: Selector<AuthState> = state => state.auth
export const isAuthenticatedSelector: Selector<boolean> = createSelector(
  [authStateSelector],
  authState => authState.isAuthenticated
)

export const ruleStateSelector: Selector<RuleState> = state => state.rule
export const ruleOverviewStateSelector: Selector<RuleOverviewState> = createSelector(
  [ruleStateSelector],
  ruleState => ruleState.ruleOverview
)
export const ruleDetailStateSelector: Selector<RuleDetailState> = createSelector(
  [ruleStateSelector],
  ruleState => ruleState.ruleDetail
)

export const routerSelector: Selector<RouterState> = state => state.router
export const locationSelector: Selector<Location> = createSelector(
  [routerSelector],
  routerState => routerState.location
)
