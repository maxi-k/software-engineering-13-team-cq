import { combineReducers } from 'redux';
import { StateType } from 'typesafe-actions';

import overviewReducer, { ruleOverviewSagas, RuleOverviewAction } from './overview'
import detailReducer, { ruleDetailSagas, RuleDetailAction } from './detail'
import creationReducer, { ruleCreationSagas, RuleCreationAction } from './creation'
import deletionReducer, { ruleDeletionSagas, RuleDeletionAction } from './deletion'

const reducer = combineReducers({
  ruleOverview: overviewReducer,
  ruleDetail: detailReducer,
  ruleCreation: creationReducer,
  ruleDeletion: deletionReducer
})

export type RuleState = StateType<typeof reducer>;
export type RuleAction = RuleOverviewAction
  | RuleDetailAction
  | RuleCreationAction
  | RuleDeletionAction

export const sagas = [
  ...ruleOverviewSagas,
  ...ruleDetailSagas,
  ...ruleCreationSagas,
  ...ruleDeletionSagas
]

export default reducer

export * from './overview'
export * from './detail'
export * from './creation'
export * from './deletion'
