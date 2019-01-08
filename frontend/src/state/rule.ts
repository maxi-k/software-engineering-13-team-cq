import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import { NotificationRuleOverview, NotificationRuleDetail } from '@/model/Rule'


export enum RuleActionType {
  RULE_VIEW = '@rule/VIEW',

  RULE_CREATE = '@rule/CREATE',
  RULE_CREATE_FINISH = '@rule/CREATE_FINISH',
  RULE_CREATE_SUCCESS = '@rule/CREATE_SUCCESS',
  RULE_CREATE_ERROR = '@rule/CREATE_SUCCESS',
  RULE_CREATE_UPDATE_FIELD = '@rule/CREATE_UPDATE_FIELD',

  RULE_UPDATE = '@rule/UPDATE',
  RULE_UPDATE_FIELD = '@rule/UPDATE_FIELD'
}
export type RuleAction = Action<RuleActionType>

export interface RuleState {
  readonly currentRule?: number,
  readonly ruleOverviewData: {[key: number]: NotificationRuleOverview},
  readonly ruleDetailData: {[key: number]: NotificationRuleDetail}
}
const initialState: RuleState = {
  ruleOverviewData: {},
  ruleDetailData: {}
}

const reducer: Reducer<RuleState> = (state = initialState, action) => {
  switch(action.type) {
    default:
      return state
  }
}

export const createRule = createAction(RuleActionType.RULE_CREATE)
export const viewRule = createAction(RuleActionType.RULE_VIEW, resolve =>
  (ruleId: number) => resolve(ruleId)
)
export const finishRuleCreation = createAsyncAction(
  RuleActionType.RULE_CREATE_FINISH,
  RuleActionType.RULE_CREATE_SUCCESS,
  RuleActionType.RULE_CREATE_ERROR
)

export default reducer
