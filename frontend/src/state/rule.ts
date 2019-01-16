import { Reducer, Action } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeLatest, takeEvery } from 'redux-saga/effects'
import { push } from 'connected-react-router'
import { NotificationRuleOverview, NotificationRuleDetail } from '@/model/Rule'
import { ensureResponseStatus } from '@/services/response-service'
import { fetchRuleOverview, fetchRuleDetail, mergeMockedRuleData, APIRule } from '@/services/rule-service'
import { FetchingData } from '@/model'

import { waitForLogin } from './auth'

export enum RuleActionType {
  RULE_CREATE_ABORT = '@rule/CREATE_ABORT',
  RULE_CREATE_FINISH = '@rule/CREATE_FINISH',
  RULE_CREATE_SUCCESS = '@rule/CREATE_SUCCESS',
  RULE_CREATE_ERROR = '@rule/CREATE_ERROR',
  RULE_CREATE_UPDATE_FIELD = '@rule/CREATE_UPDATE_FIELD',

  RULE_CREATE_SELECT_STEP = '@rule/CREATE_SELECT_STEP',
  RULE_CREATE_NEXT_STEP = '@rule/CREATE_NEXT_STEP',
  RULE_CREATE_PREVIOUS_STEP = '@rule/CREATE_PREVIOUS_STEP',

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
export interface RuleCreationState {
  readonly inProgressRule: Partial<NotificationRuleDetail>
  readonly completedSteps: Set<number>,
  readonly currentStep: number
}
export interface RuleState {
  readonly ruleCreation: RuleCreationState
  readonly ruleDetail: RuleDetailState,
  readonly ruleOverview: RuleOverviewState
}

const initialState: RuleState = {
  ruleCreation: {
    inProgressRule: {
      name: "",
      description: "",
      recipients: [],
      applyToAllFleets: true,
      fleets: []
    },
    completedSteps: new Set(),
    currentStep: 0
  },
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
    case RuleActionType.RULE_CREATE_SELECT_STEP:
      return update(state, {
        ruleCreation: {
          $merge: {
            currentStep: action.payload
          }
        }
      })
    case RuleActionType.RULE_CREATE_NEXT_STEP:
      return update(state, {
        ruleCreation: (ruleCreation: RuleCreationState) => ({
          ...ruleCreation,
          completedSteps: new Set([...Array.from(ruleCreation.completedSteps), ruleCreation.currentStep]),
          currentStep: ruleCreation.currentStep + 1
        })
      })

    case RuleActionType.RULE_CREATE_PREVIOUS_STEP:
      return update(state, {
        ruleCreation: (ruleCreation: RuleCreationState) => ({
          ...ruleCreation,
          completedSteps: update(ruleCreation.completedSteps, { $remove: [ruleCreation.currentStep] }),
          currentStep: ruleCreation.currentStep - 1
        })
      })
    case RuleActionType.RULE_CREATE_UPDATE_FIELD:
      return update(state, {
        ruleCreation: {
          inProgressRule: {
            [action.payload.fieldName]: action.payload.value
          }
        }
      });
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

export const createRuleUpdateField = createAction(RuleActionType.RULE_CREATE_UPDATE_FIELD, resolve => (
  <FieldName extends keyof NotificationRuleDetail>(fieldName: FieldName,
    value: NotificationRuleDetail[FieldName]) => (
      resolve({ fieldName, value })
    )
))
export const createRuleAbort = createAction(RuleActionType.RULE_CREATE_ABORT)
export const createRuleNextStep = createAction(RuleActionType.RULE_CREATE_NEXT_STEP)
export const createRulePreviousStep = createAction(RuleActionType.RULE_CREATE_PREVIOUS_STEP)
export const createRuleSelectStep = createAction(RuleActionType.RULE_CREATE_SELECT_STEP, resolve => (
  (step: number) => resolve(step)
))

export const finishRuleCreation = createAsyncAction(
  RuleActionType.RULE_CREATE_FINISH,
  RuleActionType.RULE_CREATE_SUCCESS,
  RuleActionType.RULE_CREATE_ERROR
)<void, NotificationRuleDetail, Error>()

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
    const response = yield call(fetchRuleOverview, authData.accessToken)
    ensureResponseStatus(response);
    const rules = yield response.json()
      .then((ruleList: APIRule[]) =>
        ruleList.map(mergeMockedRuleData)
      ) as NotificationRuleOverview[]
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
    const response = yield call(fetchRuleDetail, authData.accessToken, action.payload)
    ensureResponseStatus(response);
    const rules = yield response.json().then((result: APIRule) => mergeMockedRuleData(result)) as NotificationRuleDetail
    yield put(loadRuleDetail.success(rules))
  } catch (error) {
    yield put(loadRuleDetail.failure(error))
  }
}

function* abortRuleCreationGenerator() {
  yield put(push('/'))
}

function* ruleFetchSaga() {
  yield takeLatest(RuleActionType.RULE_OVERVIEW_FETCH, fetchRuleOverviewGenerator);
  yield takeLatest(RuleActionType.RULE_DETAIL_FETCH, fetchRuleDetailGenerator);
  yield takeEvery(RuleActionType.RULE_CREATE_ABORT, abortRuleCreationGenerator);
}

export const sagas = [
  ruleFetchSaga
]

export default reducer
