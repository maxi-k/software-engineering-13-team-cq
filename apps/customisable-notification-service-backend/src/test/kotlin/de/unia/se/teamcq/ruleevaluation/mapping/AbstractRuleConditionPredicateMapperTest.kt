package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateModel
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.lang.IllegalArgumentException

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AbstractRuleConditionPredicateMapperTest : StringSpec() {

    private var ruleConditionPredicateMapper: AbstractRuleConditionPredicateMapper = Mappers.getMapper(AbstractRuleConditionPredicateMapper::class.java)

    init {

        "Convert model to dto" {

            val ruleConditionPredicateModel = getTestRuleConditionPredicateModel()

            val ruleConditionPredicateDto = ruleConditionPredicateMapper.modelToDto(ruleConditionPredicateModel)
            val expectedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()

            ruleConditionPredicateDto shouldNotBe null
            ruleConditionPredicateDto.conditionId shouldBe expectedRuleConditionPredicateDto.conditionId
            ruleConditionPredicateDto.providerName shouldBe expectedRuleConditionPredicateDto.providerName
            ruleConditionPredicateDto.fieldName shouldBe expectedRuleConditionPredicateDto.fieldName
            ruleConditionPredicateDto.comparisonType shouldBe expectedRuleConditionPredicateDto.comparisonType
            ruleConditionPredicateDto.comparisonValue shouldBe expectedRuleConditionPredicateDto.comparisonValue
        }

        "Convert dto to model" {

            val ruleConditionPredicateDto = getTestRuleConditionPredicateDto()

            val ruleConditionPredicateModel = ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            val expectedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()

            ruleConditionPredicateModel shouldNotBe null
            ruleConditionPredicateModel.conditionId shouldBe expectedRuleConditionPredicateModel.conditionId
            ruleConditionPredicateModel.providerName shouldBe expectedRuleConditionPredicateModel.providerName
            ruleConditionPredicateModel.fieldName shouldBe expectedRuleConditionPredicateModel.fieldName
            ruleConditionPredicateModel.comparisonType shouldBe expectedRuleConditionPredicateModel.comparisonType
            ruleConditionPredicateModel.comparisonValue shouldBe expectedRuleConditionPredicateModel.comparisonValue
        }

        "Convert model to entity" {

            val ruleConditionPredicateModel = getTestRuleConditionPredicateModel()

            val ruleConditionPredicateEntity = ruleConditionPredicateMapper.modelToEntity(ruleConditionPredicateModel)
            val expectedRuleConditionPredicateEntity = getTestRuleConditionPredicateEntity()

            ruleConditionPredicateEntity shouldNotBe null
            ruleConditionPredicateEntity.conditionId shouldBe expectedRuleConditionPredicateEntity.conditionId
            ruleConditionPredicateEntity.providerName shouldBe expectedRuleConditionPredicateEntity.providerName
            ruleConditionPredicateEntity.fieldName shouldBe expectedRuleConditionPredicateEntity.fieldName
            ruleConditionPredicateEntity.comparisonType shouldBe expectedRuleConditionPredicateEntity.comparisonType
            ruleConditionPredicateEntity.comparisonValue shouldBe expectedRuleConditionPredicateEntity.comparisonValue
        }

        "Convert entity to model" {

            val ruleConditionPredicateEntity = getTestRuleConditionPredicateEntity()

            val ruleConditionPredicateModel = ruleConditionPredicateMapper.entityToModel(ruleConditionPredicateEntity)
            val expectedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()

            ruleConditionPredicateModel shouldNotBe null
            ruleConditionPredicateModel.conditionId shouldBe expectedRuleConditionPredicateModel.conditionId
            ruleConditionPredicateModel.providerName shouldBe expectedRuleConditionPredicateModel.providerName
            ruleConditionPredicateModel.fieldName shouldBe expectedRuleConditionPredicateModel.fieldName
            ruleConditionPredicateModel.comparisonType shouldBe expectedRuleConditionPredicateModel.comparisonType
            ruleConditionPredicateModel.comparisonValue shouldBe expectedRuleConditionPredicateModel.comparisonValue
        }

        "Throw an Exception if the fieldName field is null or blank" {
            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    fieldName = null
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }

            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    fieldName = ""
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }
        }

        "Throw an Exception if the providerName field is null or blank" {

            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    providerName = null
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }

            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    providerName = ""
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }
        }

        "Throw an Exception if the comparisonValue is null or blank" {

            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    comparisonValue = null
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }

            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    comparisonValue = ""
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }
        }

        "Throw an Exception if the comparisonType field is null" {
            shouldThrow<IllegalArgumentException> {

                val ruleConditionPredicateDto = getTestRuleConditionPredicateDto().apply {
                    comparisonType = null
                }

                ruleConditionPredicateMapper.dtoToModel(ruleConditionPredicateDto)
            }
        }
    }
}
