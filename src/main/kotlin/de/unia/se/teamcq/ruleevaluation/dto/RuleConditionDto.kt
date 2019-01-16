package de.unia.se.teamcq.ruleevaluation.dto

import java.io.Serializable

abstract class RuleConditionDto(

    var conditionId: Long? = 0

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
