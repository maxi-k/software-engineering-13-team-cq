package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import java.io.Serializable

class RuleConditionPredicateEntity(

    conditionId: Long? = 0,

    var providerName: String?,

    var fieldName: String?,

    var comparisonType: ComparisonType?,

    var comparisonValue: String?

) : Serializable, RuleConditionEntity(conditionId) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null)
}
