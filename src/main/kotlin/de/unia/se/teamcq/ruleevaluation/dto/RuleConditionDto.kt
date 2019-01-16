package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import java.io.Serializable

abstract class RuleConditionDto(

    var conditionId: Long? = 0,

    var logicalConnective: LogicalConnectiveType?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null)
}

class RuleConditionCompositeEntity(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var subConditions: RuleConditionDto?

) : RuleConditionDto(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}

class RuleConditionLeafEntity(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var conditionPredicate: RuleConditionPredicateDto?

) : RuleConditionDto(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
