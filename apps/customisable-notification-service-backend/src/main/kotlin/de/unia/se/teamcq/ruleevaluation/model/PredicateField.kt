package de.unia.se.teamcq.ruleevaluation.model

enum class FieldDataType {
    TEXT, INTEGER, DECIMAL, DATE, STRING_LIST, WEEK
}

enum class ComparisonType {
    EQUAL_TO, NOT_EQUAL_TO, GREATER_THAN, LESSER_THAN, GREATER_THAN_OR_EQUAL_TO, LESSER_THAN_OR_EQUAL_TO,
    CONTAINED_IN, NOT_CONTAINED_IN
}

object EvaluationStrategies {
    val NUMERIC = listOf(ComparisonType.EQUAL_TO,
        ComparisonType.NOT_EQUAL_TO,
        ComparisonType.GREATER_THAN,
        ComparisonType.LESSER_THAN,
        ComparisonType.GREATER_THAN_OR_EQUAL_TO,
        ComparisonType.LESSER_THAN_OR_EQUAL_TO
    )

    val LIST = listOf(ComparisonType.EQUAL_TO,
        ComparisonType.NOT_EQUAL_TO,
        ComparisonType.CONTAINED_IN,
        ComparisonType.NOT_CONTAINED_IN
    )

    val TEXT = listOf(ComparisonType.EQUAL_TO,
        ComparisonType.NOT_EQUAL_TO
    )
}

// Constructor with (null)-default values for everything necessary for MapStruct
data class PredicateField(

    var fieldName: String? = null,

    var dataType: FieldDataType? = null,

    var possibleEvaluationStrategies: List<ComparisonType> = mutableListOf()

)
