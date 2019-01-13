import { Reducer } from 'redux'
import { createAction, createAsyncAction } from 'typesafe-actions'
import update from 'immutability-helper'
import { call, put } from 'redux-saga/effects'

import { FetchingData } from '@/model'
import { fetchAuthenticationToken } from '@/services/auth-service'
import { ensureResponseStatus } from '@/services/response-service'

export enum AuthActionType {
  AUTH_LOGIN = '@auth/LOGIN',
  AUTH_LOGIN_SUCCESS = '@auth/LOGIN_SUCCESS',
  AUTH_LOGIN_FAILURE = '@auth/LOGIN_FAILURE',
  AUTH_LOGOUT = '@auth/LOGOUT'
}

export interface AuthData {
  accessToken: string,
  firstName: string,
  lastName: string
}
export interface AuthState extends FetchingData {
  readonly authData?: AuthData | null
}

const initialState: AuthState = {
  authData: null,
  isFetching: false,
  hasFetchError: false
}

export const reducer: Reducer<AuthState> = (state = initialState, action) => {
  switch(action.type) {
    case AuthActionType.AUTH_LOGIN:
      return update(state, {$merge: {
        isFetching: true,
        hasFetchError: false
      }})
    case AuthActionType.AUTH_LOGIN_SUCCESS:
      return update(state, {$merge: {
        authData: action.payload,
        isFetching: false,
        hasFetchError: false
      }})
    case AuthActionType.AUTH_LOGIN_FAILURE:
      return update(state, {$merge: {
        authData: null,
        isFetching: false,
        hasFetchError: action.payload || true
      }})
    case AuthActionType.AUTH_LOGOUT:
      return {
        ...initialState
      }
    default:
      return state
  }
}


export const login = createAsyncAction(
  AuthActionType.AUTH_LOGIN,
  AuthActionType.AUTH_LOGIN_SUCCESS,
  AuthActionType.AUTH_LOGIN_FAILURE
)<void, AuthData, Error>()
export const logout = createAction(AuthActionType.AUTH_LOGOUT)

function* initialLoginSaga() {
  console.info('Fetching a token with standard credentials.')
  try {
    const response = yield call(fetchAuthenticationToken)
    ensureResponseStatus(response);
    const authData = yield response.json() as AuthData
    yield put(login.success(authData))
  } catch (error) {
    yield put(login.failure(error))
  }

}

export const sagas = [
  initialLoginSaga
]
export default reducer
