package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import java.io.Serializable

abstract class RuleConditionEntity(

    var conditionId: Long? = 0,

    var logicalConnective: LogicalConnectiveType?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null)
}

class RuleConditionCompositeEntity(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var subConditions: RuleConditionEntity?

) : RuleConditionEntity(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}

class RuleConditionLeafEntity(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var conditionPredicate: RuleConditionPredicateEntity?

) : RuleConditionEntity(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
