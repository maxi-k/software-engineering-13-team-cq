package de.unia.se.teamcq.calculator

data class EvalResult(val type: String, val expression: String, val value: String?, val message: String?)

enum class Op { Add, Mul }

interface Expression
class Num(val value: Int) : Expression
class BinOp(val lhs: Expression, val op: Op, val rhs: Expression) : Expression

fun parse(text: String): Expression {
    val r = "\\s*(0|[1-9][0-9]*)\\s*(\\+|\\*)\\s*(0|[1-9][0-9]*)\\s*"
    val groups = r.toRegex().matchEntire(text)
    if (groups == null) {
        throw IllegalArgumentException("")
    }
    val op = when (groups.groupValues[2]) {
        "+" -> Op.Add
        "*" -> Op.Mul
        else -> throw IllegalArgumentException("")
    }
    val lhs = groups.groupValues[1]
    val rhs = groups.groupValues[3]
    return BinOp(Num(lhs.toInt()), op, Num(rhs.toInt()))
}

fun eval(e: Expression): Int =
        when (e) {
            is Num -> e.value
            is BinOp -> {
                val val_lhs = eval(e.lhs)
                val val_rhs = eval(e.rhs)
                when (e.op) {
                    Op.Add -> val_lhs + val_rhs
                    Op.Mul -> val_lhs * val_rhs
                }
            }
            else -> throw IllegalArgumentException("")
        }
