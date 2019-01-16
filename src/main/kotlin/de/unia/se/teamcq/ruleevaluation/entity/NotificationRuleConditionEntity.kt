package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import java.io.Serializable

abstract class NotificationRuleConditionEntity(

    var conditionId: Long? = 0,

    var logicalConnective: LogicalConnectiveType?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null)
}

class NotificationRuleConditionCompositeEntity(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var subConditions: NotificationRuleConditionEntity?

) : NotificationRuleConditionEntity(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}

class NotificationRuleConditionLeafEntity(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var conditionPredicate: NotificationRuleConditionPredicateEntity?

) : NotificationRuleConditionEntity(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
