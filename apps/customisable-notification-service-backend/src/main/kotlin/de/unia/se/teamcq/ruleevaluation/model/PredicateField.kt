package de.unia.se.teamcq.ruleevaluation.model

// Constructor with (null)-default values for everything necessary for MapStruct
data class PredicateField(

    var fieldName: String? = null,

    var dataType: FieldDataType? = null,

    var possibleEvaluationStrategies: List<ComparisonType> = mutableListOf()

)
