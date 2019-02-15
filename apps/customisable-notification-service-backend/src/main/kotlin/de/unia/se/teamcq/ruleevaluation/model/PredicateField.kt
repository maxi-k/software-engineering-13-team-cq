package de.unia.se.teamcq.ruleevaluation.model

data class PredicateField(

    var fieldName: String?,

    var dataType: FieldDataType?,

    var possibleEvaluationStrategies: List<ComparisonType>

) {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<ComparisonType>())
}
