package de.unia.se.teamcq.ruleevaluation.model

enum class LogicalConnectiveType {
    ANY, ALL
}

abstract class RuleCondition(

    var conditionId: Long? = 0,

    var logicalConnective: LogicalConnectiveType?

) {
    // Necessary for MapStruct
    constructor() : this(null, null)
}

class RuleConditionComposite(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var subConditions: List<RuleCondition>

) : RuleCondition(conditionId, logicalConnective) {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleCondition>())
}

class RuleConditionLeaf(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var conditionPredicates: List<RuleConditionPredicate>

) : RuleCondition(conditionId, logicalConnective) {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleConditionPredicate>())
}
