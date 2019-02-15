package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
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
        val foundValues = predicateFieldProviders.filter { predicateFieldProvider ->
            predicateFieldProvider.predicateFieldProviderName == predicateFieldProviderName
        }
        return when {
            foundValues.isEmpty() -> null
            foundValues.size > 1 -> {
                LOGGER.warn("Found multiple PredicateFieldProvider instances with the same name: $predicateFieldProviderName")
                foundValues[0]
            }
            else -> foundValues[0]
        }
    }

    override fun getPredicateFieldByProviderAndName(predicateFieldProviderName: String, predicateFieldName: String): PredicateField? =
            getPredicateFieldProviderByName(predicateFieldProviderName)?.let {
                val foundValues = it.predicateFields.filter { predicateField ->
                    predicateField.fieldName == predicateFieldName
                }
                return when {
                    foundValues.isEmpty() -> null
                    foundValues.size > 1 -> {
                        LOGGER.warn("Found multiple PredicateField instances with the same name for Provider $predicateFieldProviderName and Field Name $predicateFieldName")
                        foundValues[0]
                    }
                    else -> foundValues[0]
                }
            }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(PredicateFieldContainer::class.java)
    }
}
