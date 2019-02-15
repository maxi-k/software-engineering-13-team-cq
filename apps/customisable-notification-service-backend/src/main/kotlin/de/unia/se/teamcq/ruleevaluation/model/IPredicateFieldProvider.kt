package de.unia.se.teamcq.ruleevaluation.model

interface IPredicateFieldProvider {

    val predicateFieldProviderName: String

    val predicateFields: Map<String, PredicateField>

    fun predicateFieldValues(): List<PredicateField> =
            predicateFields.values.toList()
}
