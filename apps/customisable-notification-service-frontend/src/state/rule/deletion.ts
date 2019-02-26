import { Reducer, Action } from 'redux'
import { createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'

import { call, put, takeEvery } from 'redux-saga/effects'
import { push } from 'connected-react-router'
import { deleteRule } from '@/services/rule-service'
import { ensureResponseStatus } from '@/services/response-service'
import { FetchingData } from '@/model'

import { waitForLogin } from '@/state/auth'

export enum RuleDeletionActionType {
  RULE_DELETE = '@rule/delete/FETCH',
  RULE_DELETE_FAILURE = '@rule/delete/FETCH_FAILURE',
  RULE_DELETE_SUCCESS = '@rule/delete/FETCH_SUCCESS'
}
export type RuleDeletionAction = Action<RuleDeletionActionType>

export type RuleDeletionState = FetchingData & {
  lastDeletedRuleId: number | undefined
}

const initialState = {
  isFetching: false,
  hasFetchError: false,
  lastDeletedRuleId: undefined
}

const reducer: Reducer<RuleDeletionState> = (state = initialState, action) => {
  switch (action.type) {
    case RuleDeletionActionType.RULE_DELETE:
      return update(state, {
        $merge: {
          isFetching: true,
          hasFetchError: false
        }
      })
    case RuleDeletionActionType.RULE_DELETE_FAILURE:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: action.payload || true
        }
      })
    case RuleDeletionActionType.RULE_DELETE_SUCCESS:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: false,
          lastDeletedRuleId: action.payload
        }
      })
    default:
      return state
  }
}

export const deleteRemoteRule = createAsyncAction(
  RuleDeletionActionType.RULE_DELETE,
  RuleDeletionActionType.RULE_DELETE_SUCCESS,
  RuleDeletionActionType.RULE_DELETE_FAILURE
)<number, {}, Error>()

function* deleteRemoteRuleGenerator(action: ReturnType<typeof deleteRemoteRule.request>) {
  try {
    // If the authData is null, wait for the login
    // to succeed and then start fetching.
    const authData = yield call(waitForLogin)
    const response = yield call(deleteRule, authData.accessToken, action.payload)
    ensureResponseStatus(response);
    // There is no response body from the DELETE endpoint
    yield put(deleteRemoteRule.success(action.payload))
    yield put(push('/'))
  } catch (error) {
    yield put(deleteRemoteRule.failure(error))
  }
}

function* ruleDeletionSaga() {
  yield takeEvery(RuleDeletionActionType.RULE_DELETE, deleteRemoteRuleGenerator);
}

export const ruleDeletionSagas = [
  ruleDeletionSaga
]

export default reducer
