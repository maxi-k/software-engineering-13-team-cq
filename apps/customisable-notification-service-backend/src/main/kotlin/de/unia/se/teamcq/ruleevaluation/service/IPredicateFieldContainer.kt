package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.springframework.stereotype.Service

@Service
interface IPredicateFieldContainer {

    fun getPredicateFieldProviders(): Set<IPredicateFieldProvider>

    fun getPredicateFieldProviderByName(predicateFieldProviderName: String): IPredicateFieldProvider?
}
