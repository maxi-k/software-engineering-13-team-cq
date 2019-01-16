package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import java.io.Serializable

class RuleConditionPredicateDto(

    conditionId: Long? = 0,

    var providerName: String?,

    var fieldName: String?,

    var comparisonType: ComparisonType?,

    var comparisonValue: String?

) : Serializable, RuleConditionDto(conditionId) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null)
}
