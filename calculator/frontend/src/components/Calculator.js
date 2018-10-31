import React from 'react'
import { connect } from 'react-redux'
import { operator, evaluate, clearCalc } from '../reducers/calculator'

import CalculatorButton, { NumberButton } from './CalculatorButton'
import CalculationArea from './CalculationArea'

const Calculator = ({ addAction, multiplyAction, equalsAction, clearAction }) => {
    return (
        <div className="Calculator-Wrapper">
            <CalculationArea />
            { [...Array(10).keys()].map((k) => <NumberButton key={k} num={k}/>) }
            <CalculatorButton onPress={equalsAction} grid="btneq" text="=" />
            <CalculatorButton onPress={addAction} grid="btnplus" text="+" />
            <CalculatorButton onPress={multiplyAction} grid="btntimes" text="*" />
            <CalculatorButton onPress={clearAction} grid="btnclear" text="AC" />
        </div>
    )
}

const mapStateToProps = (state, ownProps) => ({

})

const mapDispatchToProps = (dispatch, ownProps) => ({
    addAction:      () => dispatch(operator('+')),
    multiplyAction: () => dispatch(operator('*')),
    equalsAction:   () => dispatch(evaluate()),
    clearAction:    () => dispatch(clearCalc())
})

export default connect(mapStateToProps, mapDispatchToProps)(Calculator)
