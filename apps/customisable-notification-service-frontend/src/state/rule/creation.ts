import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeEvery, takeLatest, select } from 'redux-saga/effects'
import { push } from 'connected-react-router'

import {
  RuleModificationState as RuleCreationState,
  initialModificationState
} from './common'
import { NotificationRuleDetail } from '@/model/Rule'
import { ruleCreationStateSelector } from '@/state/selectors'
import { convertToAPIRule, createNewRule } from '@/services/rule-service'
import { ensureResponseStatus } from '@/services/response-service'

import { waitForLogin } from '@/state/auth'

export enum RuleCreationActionType {
  RULE_CREATE_ABORT = '@rule/CREATE_ABORT',
  RULE_CREATE_FINISH = '@rule/CREATE_FINISH',
  RULE_CREATE_SUCCESS = '@rule/CREATE_SUCCESS',
  RULE_CREATE_ERROR = '@rule/CREATE_ERROR',
  RULE_CREATE_UPDATE_FIELD = '@rule/CREATE_UPDATE_FIELD',
  RULE_CREATE_SET_ERRORS = '@rule/CREATE_SET_ERRORS',

  RULE_CREATE_SELECT_STEP = '@rule/CREATE_SELECT_STEP',
  RULE_CREATE_NEXT_STEP = '@rule/CREATE_NEXT_STEP',
  RULE_CREATE_PREVIOUS_STEP = '@rule/CREATE_PREVIOUS_STEP'
}
export type RuleCreationAction = Action<RuleCreationActionType>

const initialState = { ...initialModificationState }

const reducer: Reducer<RuleCreationState> = (state = initialState, action) => {
  switch (action.type) {
    case RuleCreationActionType.RULE_CREATE_SELECT_STEP:
      return update(state, {
        $merge: {
          currentStep: action.payload
        }
      })
    case RuleCreationActionType.RULE_CREATE_NEXT_STEP:
      return update(state, (ruleCreation: RuleCreationState) => ({
        ...ruleCreation,
        completedSteps: new Set([...Array.from(ruleCreation.completedSteps), ruleCreation.currentStep]),
        currentStep: ruleCreation.currentStep + 1
      })
      )

    case RuleCreationActionType.RULE_CREATE_PREVIOUS_STEP:
      return update(state, (ruleCreation: RuleCreationState) => ({
        ...ruleCreation,
        completedSteps: update(ruleCreation.completedSteps, { $remove: [ruleCreation.currentStep] }),
        currentStep: ruleCreation.currentStep - 1
      })
      )
    case RuleCreationActionType.RULE_CREATE_UPDATE_FIELD:
      const updateMap = {
        inProgressRule: {
          [action.payload.fieldName]: action.payload.value
        }
      }
      return update(state, updateMap);
    case RuleCreationActionType.RULE_CREATE_SET_ERRORS:
      return update(state, {
        ruleErrors: { $set: action.payload }
      })
    case RuleCreationActionType.RULE_CREATE_ERROR:
    case RuleCreationActionType.RULE_CREATE_SUCCESS:
      // do nothing for now
      return state
    default:
      return state
  }
}

export const createRuleAbort = createAction(RuleCreationActionType.RULE_CREATE_ABORT)
export const createRuleNextStep = createAction(RuleCreationActionType.RULE_CREATE_NEXT_STEP)
export const createRulePreviousStep = createAction(RuleCreationActionType.RULE_CREATE_PREVIOUS_STEP)
export const createRuleSelectStep = createAction(RuleCreationActionType.RULE_CREATE_SELECT_STEP, resolve => (
  (step: number) => resolve(step)
))

export const createRuleUpdateField = createAction(RuleCreationActionType.RULE_CREATE_UPDATE_FIELD, resolve => (
  <FieldName extends keyof NotificationRuleDetail>(fieldName: FieldName,
    value: NotificationRuleDetail[FieldName]) => (
      resolve({ fieldName, value })
    )
))

export const createRuleSetErrors = createAction(RuleCreationActionType.RULE_CREATE_SET_ERRORS, resolve => (
  (errors: { [key: string]: string }) => resolve(errors)
))

export const finishRuleCreation = createAsyncAction(
  RuleCreationActionType.RULE_CREATE_FINISH,
  RuleCreationActionType.RULE_CREATE_SUCCESS,
  RuleCreationActionType.RULE_CREATE_ERROR
)<void, NotificationRuleDetail, Error>()

function* abortRuleCreationGenerator() {
  yield put(push('/'))
}

function* finishRuleCreationGenerator() {
  try {
    const authData = yield call(waitForLogin)
    const ruleData = yield select(ruleCreationStateSelector)
    const fullRule = convertToAPIRule(ruleData.inProgressRule)
    const response = yield call(createNewRule, authData.accessToken, fullRule)
    ensureResponseStatus(response);
    const rule = yield response.json() as NotificationRuleDetail
    yield put(finishRuleCreation.success(rule))
    yield put(push('/'))
  } catch (error) {
    yield put(finishRuleCreation.failure(error))
  }
}

function* ruleCreationSaga() {
  yield takeEvery(RuleCreationActionType.RULE_CREATE_ABORT, abortRuleCreationGenerator);
  yield takeLatest(RuleCreationActionType.RULE_CREATE_FINISH, finishRuleCreationGenerator)
}

export const ruleCreationSagas = [
  ruleCreationSaga
]

export default reducer
