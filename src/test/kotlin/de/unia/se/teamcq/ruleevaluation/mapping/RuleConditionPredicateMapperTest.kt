package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateModel
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class RuleConditionPredicateMapperTest : StringSpec() {

    private var ruleConditionPredicateMapper: IRuleConditionPredicateMapper = Mappers.getMapper(IRuleConditionPredicateMapper::class.java)

    init {

        "Convert model to dto" {

            val ruleConditionPredicateModel = getTestRuleConditionPredicateModel()

            val ruleConditionPredicateDto = ruleConditionPredicateMapper.modelToDto(ruleConditionPredicateModel)
            val expectedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()

            ruleConditionPredicateDto shouldNotBe null
            ruleConditionPredicateDto.predicateId shouldBe expectedRuleConditionPredicateDto.predicateId
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
            ruleConditionPredicateModel.predicateId shouldBe expectedRuleConditionPredicateModel.predicateId
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
            ruleConditionPredicateEntity.predicateId shouldBe expectedRuleConditionPredicateEntity.predicateId
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
            ruleConditionPredicateModel.predicateId shouldBe expectedRuleConditionPredicateModel.predicateId
            ruleConditionPredicateModel.providerName shouldBe expectedRuleConditionPredicateModel.providerName
            ruleConditionPredicateModel.fieldName shouldBe expectedRuleConditionPredicateModel.fieldName
            ruleConditionPredicateModel.comparisonType shouldBe expectedRuleConditionPredicateModel.comparisonType
            ruleConditionPredicateModel.comparisonValue shouldBe expectedRuleConditionPredicateModel.comparisonValue
        }
    }
}
