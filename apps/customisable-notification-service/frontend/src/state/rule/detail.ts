import { Reducer, Action } from 'redux'
import { createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'

import { call, put, takeLatest } from 'redux-saga/effects'
import { NotificationRuleDetail } from '@/model/Rule'
import { ensureResponseStatus } from '@/services/response-service'
import { fetchRuleDetail, mergeMockedRuleData, APIRule } from '@/services/rule-service'
import { FetchingData } from '@/model'

import { waitForLogin } from '@/state/auth'

export enum RuleDetailActionType {
  RULE_UPDATE = '@rule/UPDATE',
  RULE_UPDATE_FIELD = '@rule/UPDATE_FIELD',

  RULE_DETAIL_FETCH = '@rule/detail/FETCH',
  RULE_DETAIL_FETCH_FAILURE = '@rule/detail/FETCH_FAILURE',
  RULE_DETAIL_FETCH_SUCCESS = '@rule/detail/FETCH_SUCCESS'
}
export type RuleDetailAction = Action<RuleDetailActionType>

export type RuleDetailState = FetchingData & {
  rules: { [key: number]: NotificationRuleDetail }
}

const initialState = {
  isFetching: false,
  hasFetchError: false,
  rules: {}
}

const reducer: Reducer<RuleDetailState> = (state = initialState, action) => {
  switch (action.type) {
    case RuleDetailActionType.RULE_DETAIL_FETCH:
      return update(state, {
        $merge: {
          isFetching: true,
          hasFetchError: false
        }
      })

    case RuleDetailActionType.RULE_DETAIL_FETCH_FAILURE:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: action.payload || true
        }
      })

    case RuleDetailActionType.RULE_DETAIL_FETCH_SUCCESS:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: false,
          rules: {
            ...state.rules,
            [action.payload.ruleId]: action.payload
          }
        }
      })
    default:
      return state;
  }

}

export const loadRuleDetail = createAsyncAction(
  RuleDetailActionType.RULE_DETAIL_FETCH,
  RuleDetailActionType.RULE_DETAIL_FETCH_SUCCESS,
  RuleDetailActionType.RULE_DETAIL_FETCH_FAILURE
)<number, NotificationRuleDetail, Error>()

function* fetchRuleDetailGenerator(action: ReturnType<typeof loadRuleDetail.request>) {
  try {
    // If the authData is null, wait for the login
    // to succeed and then start fetching.
    const authData = yield call(waitForLogin)
    const response = yield call(fetchRuleDetail, authData.accessToken, action.payload)
    ensureResponseStatus(response);
    const rules = yield response.json().then((result: APIRule) => mergeMockedRuleData(result)) as NotificationRuleDetail
    yield put(loadRuleDetail.success(rules))
  } catch (error) {
    yield put(loadRuleDetail.failure(error))
  }
}

function* ruleDetailSaga() {
  yield takeLatest(RuleDetailActionType.RULE_DETAIL_FETCH, fetchRuleDetailGenerator);
}

export const ruleDetailSagas = [
  ruleDetailSaga
]

export default reducer
