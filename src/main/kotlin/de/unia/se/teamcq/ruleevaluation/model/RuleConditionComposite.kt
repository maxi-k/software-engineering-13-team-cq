package de.unia.se.teamcq.ruleevaluation.model

class RuleConditionComposite(

        conditionId: Long? = 0,

        var logicalConnective: LogicalConnectiveType?,

        var subConditions: List<RuleCondition>

) : RuleCondition(conditionId) {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleCondition>())
}
