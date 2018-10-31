import { combineReducers } from 'redux'
import calculatorReducer from './calculator.js'

const rootReducer = combineReducers({
    calculator: calculatorReducer
})

export { rootReducer }
