import { createSelector, Selector as StateSelector } from 'reselect'
import { RootState } from './index'
import { LanguageType, LanguageState } from './language'
import { AuthState, AuthData } from './auth'
import { RuleState, RuleOverviewState, RuleDetailState, RuleCreationState } from './rule'
import { CarParkState, CarParkListState } from './car-park'
import { PredicateFieldState, PredicateFieldListState } from './predicate-field'

import { RouterState } from 'connected-react-router'
import { Location } from 'history'
import { CarPark, Fleet } from '@/model/CarPark'

export type Selector<T> = StateSelector<RootState, T>

export const languageStateSelector: Selector<LanguageState> = state => state.language
export const languageSelector: Selector<LanguageType> = createSelector(
  [languageStateSelector],
  languageState => languageState.currentLanguage
)

export const authStateSelector: Selector<AuthState> = state => state.auth
export const authDataSelector: Selector<AuthData | null> = createSelector(
  [authStateSelector],
  authState => authState.authData
)
export const isAuthenticatedSelector: Selector<boolean> = createSelector(
  [authDataSelector],
  authData => typeof authData !== 'undefined' && authData !== null
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
export const ruleCreationStateSelector: Selector<RuleCreationState> = createSelector(
  [ruleStateSelector],
  ruleState => ruleState.ruleCreation
)

export const carParkStateSelector: Selector<CarParkState> = state => state.carPark
export const carParkListSelector: Selector<CarParkListState> = createSelector(
  [carParkStateSelector],
  carParkState => carParkState.carParks
)
export const carParkFleetsSelector: Selector<{ [key: string]: Fleet }> = createSelector(
  [carParkListSelector],
  carParks => Object.values(carParks).reduce((fleets, carPark: CarPark) => (
    carPark.fleets.reduce((carParkFleets: { [key: string]: Fleet }, fleet: Fleet) => (
      { ...carParkFleets, [fleet.fleetId]: fleet }
    ), fleets)
  ), {})
)
export const predicateFieldStateSelector: Selector<PredicateFieldState> = state => state.predicateField
export const predicateFieldListSelector: Selector<PredicateFieldListState> = createSelector(
  [predicateFieldStateSelector],
  predicateFieldState => predicateFieldState.predicateFields
)

export const routerSelector: Selector<RouterState> = state => state.router
export const locationSelector: Selector<Location> = createSelector(
  [routerSelector],
  routerState => routerState.location
)
