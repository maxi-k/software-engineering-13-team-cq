package de.unia.se.teamcq.ruleevaluation.model

enum class LogicalConnectiveType {
    ANY, ALL
}

abstract class RuleCondition(

    var conditionId: Long? = 0

) {
    // Necessary for MapStruct
    constructor() : this(null)
}
