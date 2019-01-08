import { RootState } from './index'
import { LanguageType, LanguageState } from './language'
import { AuthState } from './auth'
import { RuleState } from './rule'

export type Selector<T> = (state: RootState) => T

export const languageStateSelector: Selector<LanguageState> = state => state.language
export const languageSelector: Selector<LanguageType> = state => languageStateSelector(state).currentLanguage

export const authStateSelector: Selector<AuthState> = state => state.auth
export const isAuthenticatedSelector: Selector<boolean> = state => authStateSelector(state).isAuthenticated

export const ruleStateSelector: Selector<RuleState> = state => state.rule
