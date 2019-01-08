// TODO: FIXME: Implement with server communication
import { Reducer } from 'redux'
import { createAction } from 'typesafe-actions'

export enum AuthActionType {
  AUTH_LOGIN = '@auth/LOGIN',
  AUTH_LOGOUT = '@auth/LOGOUT'
}

export type AuthType = 'en' | 'de'
export interface AuthState {
  readonly isAuthenticated: boolean
}

const initialState: AuthState = {
  isAuthenticated: true
}

export const reducer: Reducer<AuthState> = (state = initialState, action) => {
  switch(action.type) {
    case AuthActionType.AUTH_LOGIN:
      return {
        ...state,
        isAuthenticated: true
      }
    case AuthActionType.AUTH_LOGOUT:
      return {
        ...state,
        isAuthenticated: false
      }
    default:
      return state
  }
}

export const login = createAction(AuthActionType.AUTH_LOGIN)
export const logout = createAction(AuthActionType.AUTH_LOGOUT)

export default reducer
