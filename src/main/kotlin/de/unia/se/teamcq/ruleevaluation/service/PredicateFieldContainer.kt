package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PredicateFieldContainer : IPredicateFieldContainer {

    @Autowired
    private lateinit var predicateFieldProviders: Set<IPredicateFieldProvider>

    override fun getPredicateFieldProviders(): Set<IPredicateFieldProvider> {
        return predicateFieldProviders
    }
}
