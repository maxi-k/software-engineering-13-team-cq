import React from 'react'
import { connect } from 'react-redux'
import { operator, evaluate, clearCalc } from '../reducers/calculator'

import CalcButton, { NumberButton as Num } from './CalcButton'
import CalcArea from './CalcArea'

const Calculator = ({ addAction, multiplyAction, equalsAction, clearAction }) => {
    return (
        <div className="Calculator-Wrapper">
            <CalcArea />
            { [...Array(10).keys()].map((k) => <Num key={k} num={k}/>) }
            <CalcButton onPress={equalsAction} grid="btneq" text="=" />
            <CalcButton onPress={addAction} grid="btnplus" text="+" />
            <CalcButton onPress={multiplyAction} grid="btntimes" text="*" />
            <CalcButton onPress={clearAction} grid="btnclear" text="C" />
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
