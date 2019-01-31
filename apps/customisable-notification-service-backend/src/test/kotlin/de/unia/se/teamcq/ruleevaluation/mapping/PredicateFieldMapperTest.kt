package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestPredicateFieldDto
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldModel
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class PredicateFieldMapperTest : StringSpec() {

    private var predicateFieldMapper: IPredicateFieldMapper = Mappers.getMapper(IPredicateFieldMapper::class.java)

    init {

        "Convert model to dto" {

            val predicateFieldModel = getTestPredicateFieldModel()

            val predicateFieldDto = predicateFieldMapper.modelToDto(predicateFieldModel)
            val expectedPredicateFieldDto = getTestPredicateFieldDto()

            predicateFieldDto shouldNotBe null
            predicateFieldDto.fieldName shouldBe expectedPredicateFieldDto.fieldName
            predicateFieldDto.dataType shouldBe expectedPredicateFieldDto.dataType
            predicateFieldDto.possibleEvaluationStrategies shouldBe
                    expectedPredicateFieldDto.possibleEvaluationStrategies
        }
    }
}
