import { Reducer, Action } from 'redux'
import { createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeLatest } from 'redux-saga/effects'
import { ensureResponseStatus } from '@/services/response-service'
import { FetchingData } from '@/model'

import { VehiclePredicateFields, VehicleDataType } from '@/model/Vehicle'
import { fetchPredicateFields } from '@/services/predicate-field-service'

import { waitForLogin } from './auth'

export enum PredicateFieldActionType {
  PREDICATE_FIELDS_FETCH = '@predicate-field/FETCH',
  PREDICATE_FIELDS_FETCH_FAILURE = '@predicate-field/FETCH_FAILURE',
  PREDICATE_FIELDS_FETCH_SUCCESS = '@predicate-field/FETCH_SUCCESS',
}
export type PredicateFieldAction = Action<PredicateFieldActionType>

export type PredicateFieldListState = Partial<Record<VehicleDataType, VehiclePredicateFields>>
export interface PredicateFieldState extends FetchingData {
  readonly predicateFields: PredicateFieldListState
}

const initialState = {
  predicateFields: {},
  hasFetchError: false,
  isFetching: false
}

const reducer: Reducer<PredicateFieldState> = (state = initialState, action) => {
  switch (action.type) {
    case PredicateFieldActionType.PREDICATE_FIELDS_FETCH:
      return update(state, {
        $merge: {
          isFetching: true,
          hasFetchError: false
        }
      })
    case PredicateFieldActionType.PREDICATE_FIELDS_FETCH_FAILURE:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: (action.payload || true)
        }
      })
    case PredicateFieldActionType.PREDICATE_FIELDS_FETCH_SUCCESS:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: false,
          predicateFields: action.payload.reduce(
            (obj: PredicateFieldState['predicateFields'], predicateFields: VehiclePredicateFields) => (
              update(obj, { $merge: { [predicateFields.providerName]: predicateFields } })
            ), {})
        }
      })
    default:
      return state;
  }
}

export const loadPredicateFields = createAsyncAction(
  PredicateFieldActionType.PREDICATE_FIELDS_FETCH,
  PredicateFieldActionType.PREDICATE_FIELDS_FETCH_SUCCESS,
  PredicateFieldActionType.PREDICATE_FIELDS_FETCH_FAILURE
)<void, VehiclePredicateFields[], Error>()

function* fetchPredicateFieldsGenerator() {
  try {
    // If the authData is null, wait for the login
    // to succeed and then start fetching.
    const authData = yield call(waitForLogin)
    const response = yield call(fetchPredicateFields, authData.accessToken)
    ensureResponseStatus(response.body)
    const predicateFields = yield response.json()
    yield put(loadPredicateFields.success(predicateFields))
  } catch (error) {
    yield put(loadPredicateFields.failure(error))
  }
}

function* predicateFieldFetchSaga() {
  // fetch predicate fields in the beginning
  yield fetchPredicateFieldsGenerator()
  // fetch predicate fields whenever requested
  yield takeLatest(PredicateFieldActionType.PREDICATE_FIELDS_FETCH, fetchPredicateFieldsGenerator);
}

export const sagas = [
  predicateFieldFetchSaga
]

export default reducer
