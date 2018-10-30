import { combineReducers } from 'redux'
import calcReducer from './calculator.js'

const rootReducer = combineReducers({
    calculator: calcReducer
})

export { rootReducer }
