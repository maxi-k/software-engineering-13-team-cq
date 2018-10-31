import React from 'react'
import { connect } from 'react-redux'
import { calculatorSelector } from '../selectors'

const CalculationArea = ({ calculator }) => {
    let calc = calculator || {}
    return (
        <>
          <div className="Calc-Area">
              { calc.operands && calc.operands[0] }
              {'\u00A0'}
              { calc.operator }
              {'\u00A0'}
              { calc.operands && calc.operands.length >= 2 && calc.operands[1] }
          </div>
          <div className="Calc-Result">
              { calc.result }
          </div>
        </>
    )
}

const mapStateToProps = (state, ownProps) => ({
    calculator: calculatorSelector(state)
})

const mapDispatchToProps = (dispatch, ownProps) => ({

})

export default connect(mapStateToProps, mapDispatchToProps)(CalculationArea)
