package de.unia.se.teamcq.ruleevaluation.model

interface IPredicateFieldProvider {

    val predicateFieldProviderName: String

    val predicateFields: List<PredicateField>
}
