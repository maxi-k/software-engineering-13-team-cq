package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
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

        "GetPredicateFieldProviders" {

            val mockedProvider = TestUtils.getTestPredicateFieldProviderModel()
            // We need to mock the iterator instead of the `filter` function
            // used in the #getPredicateFieldProviderByName function,
            // because filter itself is an inline function, and does not exist
            // in byte-code.
            every { mockedPredicateFieldProviders.iterator() } returns listOf(mockedProvider).iterator()

            "Find a model stored by the PredicateFieldProvider set" {
                predicateFieldContainer.getPredicateFieldProviderByName(
                        mockedProvider.predicateFieldProviderName
                ) shouldBe mockedProvider
            }

            "Return null if the model does not exist" {
                predicateFieldContainer.getPredicateFieldProviderByName(
                        "nonExistent"
                ) shouldBe null
            }
        }
    }
}
