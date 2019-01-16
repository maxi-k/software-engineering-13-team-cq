package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import java.io.Serializable

data class PredicateFieldDto(

    var predicateId: Long? = 0,

    var fieldName: String?,

    var dataType: FieldDataType?,

    var possibleEvaluationStrategies: List<ComparisonType>

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, mutableListOf<ComparisonType>())
}
