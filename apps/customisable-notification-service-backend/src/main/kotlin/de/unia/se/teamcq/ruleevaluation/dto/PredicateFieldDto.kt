package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import java.io.Serializable

// Constructor with (null)-default values for everything necessary for MapStruct
data class PredicateFieldDto(

    var fieldName: String? = null,

    var dataType: FieldDataType? = null,

    var possibleEvaluationStrategies: List<ComparisonType> = mutableListOf()

) : Serializable
