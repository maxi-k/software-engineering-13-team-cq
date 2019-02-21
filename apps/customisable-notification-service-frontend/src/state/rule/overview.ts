import { Reducer, Action } from 'redux'
import { createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeLatest } from 'redux-saga/effects'
import { NotificationRuleOverview } from '@/model/Rule'
import { ensureResponseStatus } from '@/services/response-service'
import { fetchRuleOverview, convertFromAPIRule, APIRule } from '@/services/rule-service'
import { FetchingData } from '@/model'

import { waitForLogin } from '@/state/auth'

export enum RuleOverviewActionType {
  RULE_OVERVIEW_FETCH = '@rule/overview/FETCH',
  RULE_OVERVIEW_FETCH_FAILURE = '@rule/overview/FETCH_FAILURE',
  RULE_OVERVIEW_FETCH_SUCCESS = '@rule/overview/FETCH_SUCCESS',

}
export type RuleOverviewAction = Action<RuleOverviewActionType>

export type RuleOverviewState = FetchingData & {
  rules: { [key: number]: NotificationRuleOverview }
}

const initialState: RuleOverviewState = {
  isFetching: false,
  hasFetchError: false,
  rules: {}
}

const reducer: Reducer<RuleOverviewState> = (state = initialState, action) => {
  switch (action.type) {
    case RuleOverviewActionType.RULE_OVERVIEW_FETCH:
      return update(state, {
        $merge: {
          isFetching: true,
          hasFetchError: false
        }
      })
    case RuleOverviewActionType.RULE_OVERVIEW_FETCH_FAILURE:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: action.payload || true
        }
      })
    case RuleOverviewActionType.RULE_OVERVIEW_FETCH_SUCCESS:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: false,
          rules: action.payload.reduce(
            (obj: RuleOverviewState['rules'], rule: NotificationRuleOverview) =>
              ({ ...obj, [rule.ruleId]: rule }),
            {})
        }
      })
    default:
      return state
  }
}

export const loadRuleOverview = createAsyncAction(
  RuleOverviewActionType.RULE_OVERVIEW_FETCH,
  RuleOverviewActionType.RULE_OVERVIEW_FETCH_SUCCESS,
  RuleOverviewActionType.RULE_OVERVIEW_FETCH_FAILURE
)<void, NotificationRuleOverview[], Error>()

function* fetchRuleOverviewGenerator() {
  try {
    const authData = yield call(waitForLogin)
    const response = yield call(fetchRuleOverview, authData.accessToken)
    ensureResponseStatus(response);
    const rules = yield response.json()
      .then((ruleList: APIRule[]) =>
        ruleList.map(convertFromAPIRule)
      ) as NotificationRuleOverview[]
    yield put(loadRuleOverview.success(rules))
  } catch (error) {
    yield put(loadRuleOverview.failure(error))
  }
}

function* ruleOverviewSaga() {
  yield takeLatest(RuleOverviewActionType.RULE_OVERVIEW_FETCH, fetchRuleOverviewGenerator);
}

export const ruleOverviewSagas = [
  ruleOverviewSaga,

]

export default reducer
