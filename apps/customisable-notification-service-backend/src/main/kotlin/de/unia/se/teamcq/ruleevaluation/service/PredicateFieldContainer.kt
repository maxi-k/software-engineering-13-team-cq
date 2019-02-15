package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PredicateFieldContainer : IPredicateFieldContainer {

    @Autowired
    private lateinit var predicateFieldProviders: Set<IPredicateFieldProvider>

    override fun getPredicateFieldProviders(): Set<IPredicateFieldProvider> {
        return predicateFieldProviders
    }

    override fun getPredicateFieldProviderByName(predicateFieldProviderName: String): IPredicateFieldProvider? {
        val predicateFieldProvidersWithMatchingName = predicateFieldProviders.filter { predicateFieldProvider ->
            predicateFieldProvider.predicateFieldProviderName == predicateFieldProviderName
        }
        return when {
            predicateFieldProvidersWithMatchingName.isEmpty() -> null
            predicateFieldProvidersWithMatchingName.size > 1 -> {
                LOGGER.warn(
                        "Found multiple PredicateFieldProvider instances with the same name: " +
                                predicateFieldProviderName
                )
                predicateFieldProvidersWithMatchingName[0]
            }
            else -> predicateFieldProvidersWithMatchingName[0]
        }
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(PredicateFieldContainer::class.java)
    }
}
