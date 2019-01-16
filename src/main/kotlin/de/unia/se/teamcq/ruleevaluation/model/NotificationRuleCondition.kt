package de.unia.se.teamcq.ruleevaluation.model

enum class LogicalConnectiveType {
    ANY, ALL
}

abstract class NotificationRuleCondition(

    var conditionId: Long? = 0,

    var logicalConnective: LogicalConnectiveType?

) {
    // Necessary for MapStruct
    constructor() : this(null, null)
}

class NotificationRuleConditionComposite(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var subConditions: NotificationRuleCondition?

) : NotificationRuleCondition(conditionId, logicalConnective) {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}

class NotificationRuleConditionLeaf(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var conditionPredicate: NotificationRuleConditionPredicate?

) : NotificationRuleCondition(conditionId, logicalConnective) {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
