import { mockEvaluate as remoteEval } from '../services/calculator-service'

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

const trimZeroes = (numStr) =>
      numStr.split('').every(e => e === '0')
      ? numStr
      : numStr.replace(/^0+/, '')

const addOperandLetter = (idx, newOperand, operands) => {
    if (operands.length > idx) {
        operands[idx] = trimZeroes(operands[idx] + '' + newOperand);
    } else {
        operands.push(newOperand);
    }
    return operands;
}

const calcReducer = (state = initialState, action) => {
    switch(action.type) {
    case CALC_CLEAR:
        return { ...initialState };
    case CALC_OPERAND:
        let idx = state.operator == null ? 0 : 1;
        return {
            ...state,
            operands: addOperandLetter(idx, action.operand, [...state.operands])
        }
    case CALC_OPERATOR:
        if (state.result) {
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

const maybeEval = (state, dispatch) => {
    let c = state.calculator
    if (c.operator
        && c.operands
        && c.operands.length === 2) {
        console.log('sending string to remote for evaluation');
        return remoteEval(c.operator, c.operands[0], c.operands[1])
            .then(result => dispatch(evalResult(result)))
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

const evaluate = () => (dispatch, getState) =>
      Promise.resolve(dispatch({ type: CALC_EVALUATE }))
      .then(() => maybeEval(getState(), dispatch))

const evalError = (error) => ({
    type: CALC_EVAL_ERROR,
    error
})

const evalResult = (data) => ({
    type: CALC_RESULT,
    result: data.result
})

const clearCalc = () => ({
    type: CALC_CLEAR
})

export default calcReducer
export {
    CALC_CLEAR,
    CALC_OPERAND,
    CALC_OPERATOR,
    CALC_EVALUATE,
    CALC_RESULT,
    CALC_EVAL_ERROR,

    operand, operator, evaluate, evalError, evalResult, clearCalc
}
