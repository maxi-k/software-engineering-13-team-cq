package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class RuleConditionPredicateEntity(

    conditionId: Long? = 0,

    @get: NotNull
    var providerName: String?,

    @get: NotNull
    var fieldName: String?,

    @get: NotNull
    var comparisonType: ComparisonType?,

    @get: NotNull
    var comparisonValue: String?

) : Serializable, RuleConditionEntity(conditionId) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null)
}
