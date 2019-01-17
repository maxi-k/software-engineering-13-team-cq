package de.unia.se.teamcq.ruleevaluation.model

enum class LogicalConnectiveType {
    ANY, ALL
}

abstract class RuleCondition(

    var conditionId: Long? = 0

) {

    // Autogenerated by IntelliJ
    override fun hashCode(): Int {
        return conditionId?.hashCode() ?: 0
    }

    // Autogenerated by IntelliJ
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RuleCondition) return false

        if (conditionId != other.conditionId) return false

        return true
    }

}
