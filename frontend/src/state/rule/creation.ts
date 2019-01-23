import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { put, takeEvery } from 'redux-saga/effects'
import { push } from 'connected-react-router'

// import { ensureResponseStatus } from '@/services/response-service'
import { NotificationRuleInProgress, NotificationRuleDetail, LogicalConnective } from '@/model/Rule'

export interface RuleCreationState {
  readonly inProgressRule: NotificationRuleInProgress
  readonly completedSteps: Set<number>,
  readonly currentStep: number
}
export enum RuleCreationActionType {
  RULE_CREATE_ABORT = '@rule/CREATE_ABORT',
  RULE_CREATE_FINISH = '@rule/CREATE_FINISH',
  RULE_CREATE_SUCCESS = '@rule/CREATE_SUCCESS',
  RULE_CREATE_ERROR = '@rule/CREATE_ERROR',
  RULE_CREATE_UPDATE_FIELD = '@rule/CREATE_UPDATE_FIELD',

  RULE_CREATE_SELECT_STEP = '@rule/CREATE_SELECT_STEP',
  RULE_CREATE_NEXT_STEP = '@rule/CREATE_NEXT_STEP',
  RULE_CREATE_PREVIOUS_STEP = '@rule/CREATE_PREVIOUS_STEP'
}
export type RuleCreationAction = Action<RuleCreationActionType>

const initialState = {
  inProgressRule: {
    name: "",
    description: "",
    recipients: [],
    applyToAllFleets: true,
    fleets: [],
    condition: {
      logicalConnective: LogicalConnective.Any,
      predicates: {},
    }
  },
  completedSteps: new Set(),
  currentStep: 0
}

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

export const finishRuleCreation = createAsyncAction(
  RuleCreationActionType.RULE_CREATE_FINISH,
  RuleCreationActionType.RULE_CREATE_SUCCESS,
  RuleCreationActionType.RULE_CREATE_ERROR
)<void, NotificationRuleDetail, Error>()

function* abortRuleCreationGenerator() {
  yield put(push('/'))
}

function* ruleCreationSaga() {
  yield takeEvery(RuleCreationActionType.RULE_CREATE_ABORT, abortRuleCreationGenerator);
}

export const ruleCreationSagas = [
  ruleCreationSaga
]

export default reducer
