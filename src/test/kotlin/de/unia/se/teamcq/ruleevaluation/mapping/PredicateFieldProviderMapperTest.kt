package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestPredicateFieldDto
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviderDto
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviderModel
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class PredicateFieldProviderMapperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var predicateFieldMapper: IPredicateFieldMapper

    @InjectMockKs
    lateinit var predicateFieldProviderMapper: IPredicateFieldProviderMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" {

            val expectedPredicateFieldDto = getTestPredicateFieldDto()
            every { predicateFieldMapper.modelToDto(any()) } returns expectedPredicateFieldDto

            val predicateFieldProviderModel = getTestPredicateFieldProviderModel()

            val predicateFieldProviderDto = predicateFieldProviderMapper.modelToDto(predicateFieldProviderModel)
            val expectedPredicateFieldProviderDto = getTestPredicateFieldProviderDto()

            predicateFieldProviderDto shouldNotBe null
            predicateFieldProviderDto.name shouldBe expectedPredicateFieldProviderDto.name
            predicateFieldProviderDto.predicateFields shouldContain expectedPredicateFieldDto
        }
    }
}
