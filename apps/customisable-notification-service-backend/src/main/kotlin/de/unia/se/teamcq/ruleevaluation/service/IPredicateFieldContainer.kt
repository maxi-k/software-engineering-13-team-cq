package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Service

/**
 * A container for all [IPredicateFieldProvider]s with [PredicateField]s to filter for .
 *
 * The only purpose of this class is to provide a place for [IPredicateFieldProvider]
 * to register themselves at so other classes can find them.
 *
 */
@Service
interface IPredicateFieldContainer {

    fun getPredicateFieldProviders(): Set<IPredicateFieldProvider>

    fun getPredicateFieldProviderByName(predicateFieldProviderName: String): IPredicateFieldProvider?
}
