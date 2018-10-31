import React from 'react'
import { connect } from 'react-redux'
import { operand } from '../reducers/calculator.js'

const CalculatorButton = ({ onPress, text, grid }) => (
    <div className="Calculator-Button" style={{ gridArea: grid }} onClick={onPress}>
        {text}
    </div>
)

const NumberButtonStandalone = ({ onPress, num }) => (
    <CalculatorButton onPress={onPress} text={num} grid={"btn" + num} />
)

const NumberButton = connect(
    (state, ownProps) => ({}),
    (dispatch, ownProps) => ({
        onPress: () => dispatch(operand(ownProps.num)),
    }))(NumberButtonStandalone)

export { NumberButton }
export default CalculatorButton
