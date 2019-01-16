package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import java.io.Serializable

class RuleConditionCompositeEntity(

    conditionId: Long? = 0,

    var logicalConnective: LogicalConnectiveType?,

    var subConditions: List<RuleConditionEntity>

) : RuleConditionEntity(conditionId), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleConditionEntity>())
}
