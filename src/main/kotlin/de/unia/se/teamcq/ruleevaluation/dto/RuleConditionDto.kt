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

class RuleConditionCompositeDto(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var subConditions: List<RuleConditionDto>

) : RuleConditionDto(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleConditionDto>())
}

class RuleConditionLeafDto(

    conditionId: Long? = 0,

    logicalConnective: LogicalConnectiveType?,

    var conditionPredicates: List<RuleConditionPredicateDto>

) : RuleConditionDto(conditionId, logicalConnective), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleConditionPredicateDto>())
}
