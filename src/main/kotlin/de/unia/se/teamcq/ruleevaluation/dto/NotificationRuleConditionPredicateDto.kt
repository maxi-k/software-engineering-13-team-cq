package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import java.io.Serializable

data class NotificationRuleConditionPredicateDto(

    var predicateId: Long? = 0,

    var providerName: String?,

    var comparisonType: ComparisonType?,

    var comparisonValue: String?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
