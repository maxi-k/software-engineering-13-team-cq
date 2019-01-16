package de.unia.se.teamcq.ruleevaluation.entity

import java.io.Serializable

abstract class RuleConditionEntity(

    var conditionId: Long? = 0

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
