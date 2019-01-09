import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeLatest } from 'redux-saga/effects'
import { NotificationRuleOverview, NotificationRuleDetail } from '@/model/Rule'
import { fetchRuleOverview } from '@/services/rule-service'
import { FetchingData } from '@/model'

export enum RuleActionType {
  RULE_VIEW = '@rule/VIEW',

  RULE_CREATE = '@rule/CREATE',
  RULE_CREATE_FINISH = '@rule/CREATE_FINISH',
  RULE_CREATE_SUCCESS = '@rule/CREATE_SUCCESS',
  RULE_CREATE_ERROR = '@rule/CREATE_SUCCESS',
  RULE_CREATE_UPDATE_FIELD = '@rule/CREATE_UPDATE_FIELD',

  RULE_UPDATE = '@rule/UPDATE',
  RULE_UPDATE_FIELD = '@rule/UPDATE_FIELD',

  RULE_OVERVIEW_FETCH = '@rule/overview/FETCH',
  RULE_OVERVIEW_FETCH_FAILURE = '@rule/overview/FETCH_FAILURE',
  RULE_OVERVIEW_FETCH_SUCCESS = '@rule/overview/FETCH_SUCCESS'
}
export type RuleAction = Action<RuleActionType>

export type RuleOverviewState = FetchingData & {
  rules: {[key: number]: NotificationRuleOverview}
}
export interface RuleState {
  readonly currentRule?: number,
  readonly ruleDetailData: {[key: number]: NotificationRuleDetail},
  readonly ruleOverview: RuleOverviewState
}
const initialState: RuleState = {
  ruleDetailData: {},
  ruleOverview: {
    isFetching: false,
    hasFetchError: false,
    rules: {}
  }
}

const reducer: Reducer<RuleState> = (state = initialState, action) => {
  switch(action.type) {
    case RuleActionType.RULE_OVERVIEW_FETCH:
      return update(state, {ruleOverview: {$merge: {
        isFetching: true,
        hasFetchError: false
      }}})
    case RuleActionType.RULE_OVERVIEW_FETCH_FAILURE:
      return update(state, {ruleOverview: {$merge: {
        isFetching: false,
        hasFetchError: action.payload || true
      }}})
    case RuleActionType.RULE_OVERVIEW_FETCH_SUCCESS:
      return update(state, {ruleOverview: {$merge: {
        isFetching: false,
        hasFetchError: false,
        rules: action.payload.reduce(
          (obj: RuleOverviewState['rules'], rule: NotificationRuleOverview) =>
            ({...obj, [rule.id]: rule}),
          {})
      }}})
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

export const loadRuleOverview = createAsyncAction(
  RuleActionType.RULE_OVERVIEW_FETCH,
  RuleActionType.RULE_OVERVIEW_FETCH_SUCCESS,
  RuleActionType.RULE_OVERVIEW_FETCH_FAILURE
)<void, NotificationRuleOverview[], Error>()

function* fetchRuleOverviewGenerator() {
  try {
    const response = yield call(fetchRuleOverview)
    const rules = yield response.json() as NotificationRuleOverview[]
    yield put(loadRuleOverview.success(rules))
  } catch (error) {
    yield put(loadRuleOverview.failure(error))
  }
}

function* ruleOverviewFetchSaga() {
  yield takeLatest(RuleActionType.RULE_OVERVIEW_FETCH, fetchRuleOverviewGenerator);

}

export const sagas = [
  ruleOverviewFetchSaga
]

export default reducer
