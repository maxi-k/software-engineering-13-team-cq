package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import java.io.Serializable

class RuleConditionCompositeDto(

        conditionId: Long? = 0,

        var logicalConnective: LogicalConnectiveType?,

        var subConditions: List<RuleConditionDto>

) : RuleConditionDto(conditionId), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleConditionDto>())
}
