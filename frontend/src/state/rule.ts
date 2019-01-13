import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeLatest } from 'redux-saga/effects'
import { NotificationRuleOverview, NotificationRuleDetail } from '@/model/Rule'
import { ensureResponseStatus } from '@/services/response-service'
import { fetchRuleOverview, fetchRuleDetail } from '@/services/rule-service'
import { FetchingData } from '@/model'

import { waitForLogin } from './auth'

export enum RuleActionType {
  RULE_CREATE = '@rule/CREATE',
  RULE_CREATE_FINISH = '@rule/CREATE_FINISH',
  RULE_CREATE_SUCCESS = '@rule/CREATE_SUCCESS',
  RULE_CREATE_ERROR = '@rule/CREATE_ERROR',
  RULE_CREATE_UPDATE_FIELD = '@rule/CREATE_UPDATE_FIELD',

  RULE_UPDATE = '@rule/UPDATE',
  RULE_UPDATE_FIELD = '@rule/UPDATE_FIELD',

  RULE_OVERVIEW_FETCH = '@rule/overview/FETCH',
  RULE_OVERVIEW_FETCH_FAILURE = '@rule/overview/FETCH_FAILURE',
  RULE_OVERVIEW_FETCH_SUCCESS = '@rule/overview/FETCH_SUCCESS',

  RULE_DETAIL_FETCH = '@rule/detail/FETCH',
  RULE_DETAIL_FETCH_FAILURE = '@rule/detail/FETCH_FAILURE',
  RULE_DETAIL_FETCH_SUCCESS = '@rule/detail/FETCH_SUCCESS'
}
export type RuleAction = Action<RuleActionType>

export type RuleOverviewState = FetchingData & {
  rules: { [key: number]: NotificationRuleOverview }
}
export type RuleDetailState = FetchingData & {
  rules: { [key: number]: NotificationRuleDetail }
}
export interface RuleState {
  readonly currentRule?: number,
  readonly ruleDetail: RuleDetailState,
  readonly ruleOverview: RuleOverviewState
}
const initialState: RuleState = {
  ruleDetail: {
    isFetching: false,
    hasFetchError: false,
    rules: {}
  },
  ruleOverview: {
    isFetching: false,
    hasFetchError: false,
    rules: {}
  }
}

const reducer: Reducer<RuleState> = (state = initialState, action) => {
  switch (action.type) {
    case RuleActionType.RULE_OVERVIEW_FETCH:
      return update(state, {
        ruleOverview: {
          $merge: {
            isFetching: true,
            hasFetchError: false
          }
        }
      })
    case RuleActionType.RULE_DETAIL_FETCH:
      return update(state, {
        ruleDetail: {
          $merge: {
            isFetching: true,
            hasFetchError: false
          }
        }
      })
    case RuleActionType.RULE_OVERVIEW_FETCH_FAILURE:
      return update(state, {
        ruleOverview: {
          $merge: {
            isFetching: false,
            hasFetchError: action.payload || true
          }
        }
      })
    case RuleActionType.RULE_DETAIL_FETCH_FAILURE:
      return update(state, {
        ruleDetail: {
          $merge: {
            isFetching: false,
            hasFetchError: action.payload || true
          }
        }
      })
    case RuleActionType.RULE_OVERVIEW_FETCH_SUCCESS:
      return update(state, {
        ruleOverview: {
          $merge: {
            isFetching: false,
            hasFetchError: false,
            rules: action.payload.reduce(
              (obj: RuleOverviewState['rules'], rule: NotificationRuleOverview) =>
                ({ ...obj, [rule.ruleId]: rule }),
              {})
          }
        }
      })
    case RuleActionType.RULE_DETAIL_FETCH_SUCCESS:
      return update(state, {
        ruleDetail: {
          $merge: {
            isFetching: false,
            hasFetchError: false,
            rules: {
              ...state.ruleDetail.rules,
              [action.payload.ruleId]: action.payload
            }
          }
        }
      })
    default:
      return state
  }
}

export const createRule = createAction(RuleActionType.RULE_CREATE)
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

export const loadRuleDetail = createAsyncAction(
  RuleActionType.RULE_DETAIL_FETCH,
  RuleActionType.RULE_DETAIL_FETCH_SUCCESS,
  RuleActionType.RULE_DETAIL_FETCH_FAILURE
)<number, NotificationRuleDetail, Error>()

function* fetchRuleOverviewGenerator() {
  try {
    const authData = yield call(waitForLogin)
    console.log(authData)
    const response = yield call(fetchRuleOverview, authData.accessToken)
    ensureResponseStatus(response);
    const rules = yield response.json() as NotificationRuleOverview[]
    yield put(loadRuleOverview.success(rules))
  } catch (error) {
    yield put(loadRuleOverview.failure(error))
  }
}

function* fetchRuleDetailGenerator(action: ReturnType<typeof loadRuleDetail.request>) {
  try {
    // If the authData is null, wait for the login
    // to succeed and then start fetching.
    const authData = yield call(waitForLogin)
    console.log(authData)
    const response = yield call(fetchRuleDetail, authData.accessToken, action.payload)
    ensureResponseStatus(response);
    const rules = yield response.json() as NotificationRuleDetail
    yield put(loadRuleDetail.success(rules))
  } catch (error) {
    yield put(loadRuleDetail.failure(error))
  }
}

function* ruleFetchSaga() {
  yield takeLatest(RuleActionType.RULE_OVERVIEW_FETCH, fetchRuleOverviewGenerator);
  yield takeLatest(RuleActionType.RULE_DETAIL_FETCH, fetchRuleDetailGenerator);
}

export const sagas = [
  ruleFetchSaga
]

export default reducer
