package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviderModel
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviderDto
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class PredicateFieldProviderMapperTest : StringSpec() {

    private var predicateFieldMapper: IPredicateFieldMapper = Mappers.getMapper(IPredicateFieldMapper::class.java)

    init {

        "Convert model to dto" {

            val predicateFieldProviderModel = getTestPredicateFieldProviderModel()

            val predicateFieldProviderDto = predicateFieldMapper.modelToDto(predicateFieldProviderModel)
            val expectedPredicateFieldProviderDto = getTestPredicateFieldProviderDto()

            predicateFieldProviderDto shouldNotBe null
            predicateFieldProviderDto.name shouldBe expectedPredicateFieldProviderDto.name
        }
    }
}
