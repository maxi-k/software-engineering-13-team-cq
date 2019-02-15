package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PredicateFieldContainer : IPredicateFieldContainer {

    @Autowired
    private lateinit var predicateFieldProviders: Set<IPredicateFieldProvider>

    override fun getPredicateFieldProviders(): Set<IPredicateFieldProvider> {
        return predicateFieldProviders
    }

    override fun getPredicateFieldProviderByName(predicateFieldProviderName: String): IPredicateFieldProvider? =
            predicateFieldProviders.find { predicateFieldProvider ->
                predicateFieldProvider.predicateFieldProviderName == predicateFieldProviderName
            }

    override fun getPredicateFieldByProviderAndName(predicateFieldProviderName: String, predicateFieldName: String): PredicateField? =
            getPredicateFieldProviderByName(predicateFieldProviderName)?.let {
                it.predicateFields.find { predicateField ->
                    predicateField.fieldName == predicateFieldName
                }
            }
}
