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

    /**
     * Get all available [IPredicateFieldProvider]s
     *
     * @returns Available [IPredicateFieldProvider]s
     */
    fun getPredicateFieldProviders(): Set<IPredicateFieldProvider>

    /**
     * Get a [IPredicateFieldProvider] by name
     *
     * @param predicateFieldProviderName The name to look for
     * @returns A [IPredicateFieldProvider] with that name if it exists
     */
    fun getPredicateFieldProviderByName(predicateFieldProviderName: String): IPredicateFieldProvider?
}
