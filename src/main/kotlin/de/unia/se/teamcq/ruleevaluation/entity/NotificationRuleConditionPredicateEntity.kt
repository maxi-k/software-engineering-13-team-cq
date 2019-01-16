package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import java.io.Serializable

data class NotificationRuleConditionPredicateEntity(

    var predicateId: Long? = 0,

    var providerName: String?,

    var comparisonType: ComparisonType?,

    var comparisonValue: String?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
