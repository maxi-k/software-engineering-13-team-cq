import { combineReducers } from 'redux';
import { StateType } from 'typesafe-actions';

import overviewReducer, { ruleOverviewSagas, RuleOverviewAction } from './overview'
import detailReducer, { ruleDetailSagas, RuleDetailAction } from './detail'
import creationReducer, { ruleCreationSagas, RuleCreationAction } from './creation'

const reducer = combineReducers({
  ruleOverview: overviewReducer,
  ruleDetail: detailReducer,
  ruleCreation: creationReducer
})

export type RuleState = StateType<typeof reducer>;
export type RuleAction = RuleOverviewAction | RuleDetailAction | RuleCreationAction

export const sagas = [
  ...ruleOverviewSagas,
  ...ruleDetailSagas,
  ...ruleCreationSagas
]

export default reducer

export * from './overview'
export * from './detail'
export * from './creation'
