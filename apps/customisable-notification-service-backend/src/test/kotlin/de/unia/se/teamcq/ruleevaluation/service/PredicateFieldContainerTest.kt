package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class PredicateFieldContainerTest : StringSpec() {

    @MockK
    lateinit var mockedPredicateFieldProviders: Set<IPredicateFieldProvider>

    @InjectMockKs
    lateinit var predicateFieldContainer: PredicateFieldContainer

    init {
        MockKAnnotations.init(this)

        "GetPredicateFieldProviders should return expected PredicateFieldProviders" {

            val predicateFieldProviders = predicateFieldContainer.getPredicateFieldProviders()
            predicateFieldProviders shouldBe mockedPredicateFieldProviders
        }
    }
}
