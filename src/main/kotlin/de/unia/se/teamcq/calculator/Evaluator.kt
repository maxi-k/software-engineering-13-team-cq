package de.unia.se.teamcq.calculator

data class EvalResult(val type: String, val expression: String, val value: String?, val message: String?)

enum class Operator { Add, Mul }

interface Expression
class Number(val value: Int) : Expression
class BinaryOperation(val leftSide: Expression, val operator: Operator, val rightSide: Expression) : Expression

class Evaluator {

    companion object {
        fun parseAndEvaluate(expression: String): Int =
            evaluate(parse(expression))

        private fun parse(expression: String): Expression {
            val regex = "\\s*(0|[1-9][0-9]*)\\s*(\\+|\\*)\\s*(0|[1-9][0-9]*)\\s*".toRegex()
            val groups = regex.matchEntire(expression) ?: throw IllegalArgumentException("")

            val operator = when (groups.groupValues[2]) {
                "+" -> Operator.Add
                "*" -> Operator.Mul
                else -> throw IllegalArgumentException("Unknown Operator!")
            }

            val leftNumber = groups.groupValues[1]
            val rightNumber = groups.groupValues[3]

            return BinaryOperation(Number(leftNumber.toInt()), operator, Number(rightNumber.toInt()))
        }

        private fun evaluate(expression: Expression): Int =
            when (expression) {
                is Number -> expression.value
                is BinaryOperation -> {
                    val leftNumber = evaluate(expression.leftSide)
                    val rightNumber = evaluate(expression.rightSide)
                    when (expression.operator) {
                        Operator.Add -> leftNumber + rightNumber
                        Operator.Mul -> leftNumber * rightNumber
                    }
                }
                else -> throw IllegalArgumentException("Unknown Operator!")
            }
    }
}
