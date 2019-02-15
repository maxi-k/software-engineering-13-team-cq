package de.unia.se.teamcq.ruleevaluation.model

interface IPredicateFieldProvider {

    val predicateFieldProviderName: String

    val predicateFields: List<PredicateField>

    fun getPredicateFieldsByName(predicateFieldName: String): List<PredicateField> =
            predicateFields.filter { predicateField ->
                predicateField.fieldName == predicateFieldName
            }
}
