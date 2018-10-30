import React from 'react'
import { connect } from 'react-redux'
import { operand } from '../reducers/calculator.js'

const CalcButton = ({ onPress, text, grid }) => (
    <div className="Calculator-Button" style={{ gridArea: grid }} onClick={onPress}>
      {text}
    </div>
)

const NumButton = ({ onPress, num }) =>
      <CalcButton onPress={onPress} text={num} grid={"btn" + num} />

const NumberButton = connect(
    (state, ownProps) => ({}),
    (dispatch, ownProps) => ({
        onPress: () => dispatch(operand(ownProps.num)),
    }))(NumButton)

export { NumberButton }
export default CalcButton
