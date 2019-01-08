import { Reducer } from 'redux'
import { createAction } from 'typesafe-actions'

export enum LanguageActionType {
  SWITCH_LANGUAGE = '@language/SWITCH'
}

export type LanguageType = 'en' | 'de'
export interface LanguageState {
  readonly currentLanguage: 'en' | 'de'
}

const initialState: LanguageState = {
  currentLanguage: 'en'
}

export const reducer: Reducer<LanguageState> = (state = initialState, action) => {
  switch(action.type) {
    case LanguageActionType.SWITCH_LANGUAGE:
      return {
        ...state,
        currentLanguage: action.payload
      }
    default:
      return state
  }
}
// export type LanguageState = StateType<typeof reducer>

export const switchLanguage = createAction(LanguageActionType.SWITCH_LANGUAGE, resolve =>
  (language: LanguageType) => resolve(language)
)

export default reducer
