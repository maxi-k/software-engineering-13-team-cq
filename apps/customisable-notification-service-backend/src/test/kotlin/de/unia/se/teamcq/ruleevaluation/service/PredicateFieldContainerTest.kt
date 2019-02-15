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

        /* Disabled because mock does not work, see comment below */
        "GetPredicateFieldProviders".config(enabled = false) {

            val mockedProvider = TestUtils.getTestPredicateFieldProviderModel()
            /*
             This doesn't work for some reason:
             io.mockk.MockKException: Failed matching mocking signature for SignedCall(...)
            */
            every { mockedPredicateFieldProviders.filter(captureLambda()) } returns listOf(mockedProvider)

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
