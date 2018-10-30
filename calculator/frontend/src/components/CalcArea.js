import React from 'react'
import { connect } from 'react-redux'
import { calcSelector } from '../selectors'

const CalcArea = ({ calculator }) => {
    let calc = calculator || {}
    return (
        <>
          <div className="Calc-Area">
            { calc.operands && calc.operands[0] }
            {' '}
            { calc.operator }
            {' '}
            { calc.operands && calc.operands.length >= 2 && calc.operands[1] }
          </div>
          <div className="Calc-Result">
            { calc.result }
          </div>
        </>
    )
}

const mapStateToProps = (state, ownProps) => ({
    calculator: calcSelector(state)
})

const mapDispatchToProps = (dispatch, ownProps) => ({

})

export default connect(mapStateToProps, mapDispatchToProps)(CalcArea)
