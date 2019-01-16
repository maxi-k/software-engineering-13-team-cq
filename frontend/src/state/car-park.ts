import { Reducer, Action } from 'redux'
import { createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put, takeLatest } from 'redux-saga/effects'
import { ensureResponseStatus } from '@/services/response-service'
import { FetchingData } from '@/model'

import { CarPark } from '@/model/CarPark'
import { fetchCarParks, CarParkAPIResponse } from '@/services/car-park-service'

import { waitForLogin } from './auth'

export enum CarParkActionType {
  CAR_PARKS_FETCH = '@car-park/FETCH',
  CAR_PARKS_FETCH_FAILURE = '@car-park/FETCH_FAILURE',
  CAR_PARKS_FETCH_SUCCESS = '@car-park/FETCH_SUCCESS',
}
export type CarParkAction = Action<CarParkActionType>

export interface CarParkListState {
  [key: string]: CarPark
}

export interface CarParkState extends FetchingData {
  readonly carParks: CarParkListState
}

const initialState = {
  carParks: {},
  hasFetchError: false,
  isFetching: false
}

const reducer: Reducer<CarParkState> = (state = initialState, action) => {
  switch (action.type) {
    case CarParkActionType.CAR_PARKS_FETCH:
      return update(state, {
        $merge: {
          isFetching: true,
          hasFetchError: false
        }
      })
    case CarParkActionType.CAR_PARKS_FETCH_FAILURE:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: (action.payload || true)
        }
      })
    case CarParkActionType.CAR_PARKS_FETCH_SUCCESS:
      return update(state, {
        $merge: {
          isFetching: false,
          hasFetchError: false,
          carParks: action.payload.reduce(
            (obj: CarParkState['carParks'], carPark: CarPark) => (
              update(obj, { $merge: { [carPark.id]: carPark } })
            ), {})
        }
      })
    default:
      return state;
  }
}

export const loadCarParks = createAsyncAction(
  CarParkActionType.CAR_PARKS_FETCH,
  CarParkActionType.CAR_PARKS_FETCH_SUCCESS,
  CarParkActionType.CAR_PARKS_FETCH_FAILURE
)<void, CarParkAPIResponse, Error>()

function* fetchCarParksGenerator() {
  try {
    // If the authData is null, wait for the login
    // to succeed and then start fetching.
    const authData = yield call(waitForLogin)
    const response = yield call(fetchCarParks, authData.accessToken)
    ensureResponseStatus(response.body)
    const carParks = yield response.json().then((result: CarParkAPIResponse) => result.items)
    yield put(loadCarParks.success(carParks))
  } catch (error) {
    yield put(loadCarParks.failure(error))
  }
}

function* carParkFetchSaga() {
  // fetch car parks in the beginning
  yield fetchCarParksGenerator()
  // fetch car parks whenever requested
  yield takeLatest(CarParkActionType.CAR_PARKS_FETCH, fetchCarParksGenerator);
}

export const sagas = [
  carParkFetchSaga
]

export default reducer
