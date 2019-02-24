import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeEvery, takeLatest, select } from 'redux-saga/effects'
import { push } from 'connected-react-router'

import {
  RuleModificationState,
  initialModificationState
} from './common'
import { NotificationRuleDetail } from '@/model/Rule'
import { ruleEditingStateSelector } from '@/state/selectors'
import {
  APIRule,
  convertToAPIRule,
  editRule,
  fetchRuleDetail,
  convertFromAPIRule
} from '@/services/rule-service'
import { ensureResponseStatus } from '@/services/response-service'

import { waitForLogin } from '@/state/auth'
import { FetchingAttributes } from '@/model';

export enum RuleEditingActionType {
  RULE_EDIT_FETCH_INITIAL = '@rule/EDIT_FETCH_INITIAL',
  RULE_EDIT_FETCH_INITIAL_SUCCESS = '@rule/EDIT_FETCH_INITIAL_SUCCESS',
  RULE_EDIT_FETCH_INITIAL_FAILURE = '@rule/EDIT_FETCH_INITIAL_FAILURE',

  RULE_EDIT_ABORT = '@rule/EDIT_ABORT',
  RULE_EDIT_FINISH = '@rule/EDIT_FINISH',
  RULE_EDIT_SUCCESS = '@rule/EDIT_SUCCESS',
  RULE_EDIT_ERROR = '@rule/EDIT_ERROR',
  RULE_EDIT_UPDATE_FIELD = '@rule/EDIT_UPDATE_FIELD',

  RULE_EDIT_SELECT_STEP = '@rule/EDIT_SELECT_STEP',
  RULE_EDIT_NEXT_STEP = '@rule/EDIT_NEXT_STEP',
  RULE_EDIT_PREVIOUS_STEP = '@rule/EDIT_PREVIOUS_STEP'
}
export type RuleEditingAction = Action<RuleEditingActionType>
export type RuleEditingState = RuleModificationState & FetchingAttributes

const initialState = {
  ...initialModificationState,
  isFetching: false,
  hasFetchError: false
}

const reducer: Reducer<RuleEditingState> = (state = initialState, action) => {
  switch (action.type) {
    case RuleEditingActionType.RULE_EDIT_FETCH_INITIAL:
      return update(state, {
        $merge: {
          isFetching: true,
          hasFetchError: false
        }
      })
    case RuleEditingActionType.RULE_EDIT_FETCH_INITIAL_SUCCESS:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: false,
          inProgressRule: {
            ...action.payload
          }
        }
      })
    case RuleEditingActionType.RULE_EDIT_FETCH_INITIAL_FAILURE:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: action.payload || true
        }
      })
    case RuleEditingActionType.RULE_EDIT_SELECT_STEP:
      return update(state, {
        $merge: {
          currentStep: action.payload
        }
      })
    case RuleEditingActionType.RULE_EDIT_NEXT_STEP:
      return update(state, (ruleEditing: RuleEditingState) => ({
        ...ruleEditing,
        completedSteps: new Set([...Array.from(ruleEditing.completedSteps), ruleEditing.currentStep]),
        currentStep: ruleEditing.currentStep + 1
      })
      )

    case RuleEditingActionType.RULE_EDIT_PREVIOUS_STEP:
      return update(state, (ruleEditing: RuleEditingState) => ({
        ...ruleEditing,
        completedSteps: update(ruleEditing.completedSteps, { $remove: [ruleEditing.currentStep] }),
        currentStep: ruleEditing.currentStep - 1
      })
      )
    case RuleEditingActionType.RULE_EDIT_UPDATE_FIELD:
      const updateMap = {
        inProgressRule: {
          [action.payload.fieldName]: action.payload.value
        }
      }
      return update(state, updateMap);
    case RuleEditingActionType.RULE_EDIT_ERROR:
      return state
    case RuleEditingActionType.RULE_EDIT_SUCCESS:
      // do nothing for now
      return update(state, {
        $merge: {
          ...initialModificationState
        }
      })
    default:
      return state
  }
}

export const editRuleAbort = createAction(RuleEditingActionType.RULE_EDIT_ABORT)
export const editRuleNextStep = createAction(RuleEditingActionType.RULE_EDIT_NEXT_STEP)
export const editRulePreviousStep = createAction(RuleEditingActionType.RULE_EDIT_PREVIOUS_STEP)
export const editRuleSelectStep = createAction(RuleEditingActionType.RULE_EDIT_SELECT_STEP, resolve => (
  (step: number) => resolve(step)
))

export const editRuleUpdateField = createAction(RuleEditingActionType.RULE_EDIT_UPDATE_FIELD, resolve => (
  <FieldName extends keyof NotificationRuleDetail>(fieldName: FieldName,
    value: NotificationRuleDetail[FieldName]) => (
      resolve({ fieldName, value })
    )
))

export const loadEditingRuleInitial = createAsyncAction(
  RuleEditingActionType.RULE_EDIT_FETCH_INITIAL,
  RuleEditingActionType.RULE_EDIT_FETCH_INITIAL_SUCCESS,
  RuleEditingActionType.RULE_EDIT_FETCH_INITIAL_FAILURE
)<number, NotificationRuleDetail, Error>()

export const finishRuleEditing = createAsyncAction(
  RuleEditingActionType.RULE_EDIT_FINISH,
  RuleEditingActionType.RULE_EDIT_SUCCESS,
  RuleEditingActionType.RULE_EDIT_ERROR
)<void, NotificationRuleDetail, Error>()

function* abortRuleEditingGenerator() {
  yield put(push('/'))
}

function* fetchEditingRuleInitial(action: ReturnType<typeof loadEditingRuleInitial.request>) {
  try {
    // If the authData is null, wait for the login
    // to succeed and then start fetching.
    const authData = yield call(waitForLogin)
    const response = yield call(fetchRuleDetail, authData.accessToken, action.payload)
    ensureResponseStatus(response);
    const rule = yield response.json().then((result: APIRule) => convertFromAPIRule(result)) as NotificationRuleDetail
    yield put(loadEditingRuleInitial.success(rule))
  } catch (error) {
    yield put(loadEditingRuleInitial.failure(error))
  }
}

function* finishRuleEditingGenerator() {
  try {
    const authData = yield call(waitForLogin)
    const ruleData = yield select(ruleEditingStateSelector)
    const fullRule = convertToAPIRule(ruleData.inProgressRule)
    const response = yield call(editRule, authData.accessToken, fullRule)
    ensureResponseStatus(response);
    const rule = yield response.json() as NotificationRuleDetail
    yield put(finishRuleEditing.success(rule))
    // yield put(push(`/rule/${rule.ruleId}`))
    yield put(push('/'))
  } catch (error) {
    yield put(finishRuleEditing.failure(error))
  }
}

function* ruleEditingSaga() {
  yield takeEvery(RuleEditingActionType.RULE_EDIT_ABORT, abortRuleEditingGenerator)
  yield takeLatest(RuleEditingActionType.RULE_EDIT_FINISH, finishRuleEditingGenerator)
  yield takeEvery(RuleEditingActionType.RULE_EDIT_FETCH_INITIAL, fetchEditingRuleInitial)
}

export const ruleEditingSagas = [
  ruleEditingSaga
]

export default reducer
