import { evaluate as remoteEvaluate } from '../services/calculator-service'

const CALC_CLEAR      = 'calc/clear'
const CALC_OPERAND    = 'calc/operand'
const CALC_OPERATOR   = 'calc/operator'
const CALC_EVALUATE   = 'calc/evaluate'
const CALC_RESULT     = 'calc/result'
const CALC_EVAL_ERROR = 'calc/error'

const initialState = {
    operator: null,
    operands: [],
    result: null,
    loading: false,
    error: false,
}

const trimZeroes = (numStr) => (
    numStr.length === 1
        ? numStr
        : numStr.replace(/^0/, '')
)

const addOperandLetter = (index, newOperand, operands) => {
    if (operands.length > index) {
        operands[index] = trimZeroes(operands[index] + '' + newOperand);
    } else {
        operands.push(newOperand);
    }
    return operands;
}

const calculatorReducer = (state = initialState, action) => {
    switch(action.type) {
    case CALC_CLEAR:
        return { ...initialState };
    case CALC_OPERAND:
        let idx = state.operator === null ? 0 : 1;
        return {
            ...state,
            operands: addOperandLetter(idx, action.operand, [...state.operands])
        }
    case CALC_OPERATOR:
        if (state.result && state.operands.length === 0) {
            return {
                ...state,
                operator: action.operator,
                operands: [ state.result ]
            }
        } else {
            return {
                ...state,
                operator: action.operator
            }
        }
    case CALC_RESULT:
        return {
            ...state,
            operator: null,
            operands: [],
            loading: false,
            error: false,
            result: action.result
        }
    case CALC_EVALUATE:
        return {
            ...state,
            loading: true
        }
    case CALC_EVAL_ERROR:
        return {
            ...state,
            loading: false,
            error: action.error
        }
    default:
        return state
    }
}

const maybeEvaluate = (state, dispatch) => {
    let { operator, operands } = state.calculator
    if (operator
        && operands
        && operands.length === 2) {
        return remoteEvaluate(operator, operands[0], operands[1])
            .then(result => (
                result
                    .json()
                    .then((json) => dispatch(evalResult(json)))
            ))
            .catch(error => dispatch(evalError(error)))
    }
}

const operand = (string) => ({
    type: CALC_OPERAND,
    operand: string
})


const operator = (string) => ({
    type: CALC_OPERATOR,
    operator: string
})

const evaluate = () => (dispatch, getState) => (
    Promise
        .resolve(dispatch({ type: CALC_EVALUATE }))
        .then(() => maybeEvaluate(getState(), dispatch))
)

const evalError = (error) => ({
    type: CALC_EVAL_ERROR,
    error
})

const evalResult = (data) => ({
    type: CALC_RESULT,
    result: data.value
})

const clearCalc = () => ({
    type: CALC_CLEAR
})

export default calculatorReducer
export {
    CALC_CLEAR,
    CALC_OPERAND,
    CALC_OPERATOR,
    CALC_EVALUATE,
    CALC_RESULT,
    CALC_EVAL_ERROR,

    operand, operator, evaluate, evalError, evalResult, clearCalc
}
