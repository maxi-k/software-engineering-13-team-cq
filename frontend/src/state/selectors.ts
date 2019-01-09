import { createSelector, Selector as StateSelector } from 'reselect'
import { RootState } from './index'
import { LanguageType, LanguageState } from './language'
import { AuthState } from './auth'
import { RuleState, RuleOverviewState } from './rule'

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
