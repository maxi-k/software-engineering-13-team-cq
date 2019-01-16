package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestRuleConditionLeafDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionLeafEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionLeafModel
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateModel
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
class RuleConditionLeafMapperTest : StringSpec() {

    @MockK
    lateinit var ruleConditionPredicateMapper: IRuleConditionPredicateMapper

    @InjectMockKs
    lateinit var ruleConditionLeafMapper: IRuleConditionLeafMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" {

            val mockedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()
            every { ruleConditionPredicateMapper.modelToDto(any()) } returns mockedRuleConditionPredicateDto

            val ruleConditionLeafModel = getTestRuleConditionLeafModel()

            val ruleConditionLeafDto = ruleConditionLeafMapper.modelToDto(ruleConditionLeafModel)
            val expectedRuleConditionLeafDto = getTestRuleConditionLeafDto()

            ruleConditionLeafDto shouldNotBe null
            ruleConditionLeafDto.conditionId shouldBe expectedRuleConditionLeafDto.conditionId
            ruleConditionLeafDto.logicalConnective shouldBe expectedRuleConditionLeafDto.logicalConnective
            ruleConditionLeafDto.conditionPredicates shouldContain mockedRuleConditionPredicateDto
        }

        "Convert dto to model" {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.dtoToModel(any()) } returns mockedRuleConditionPredicateModel

            val ruleConditionLeafDto = getTestRuleConditionLeafDto()

            val ruleConditionLeafModel = ruleConditionLeafMapper.dtoToModel(ruleConditionLeafDto)
            val expectedRuleConditionLeafModel = getTestRuleConditionLeafModel()

            ruleConditionLeafModel shouldNotBe null
            ruleConditionLeafModel.conditionId shouldBe expectedRuleConditionLeafModel.conditionId
            ruleConditionLeafModel.logicalConnective shouldBe expectedRuleConditionLeafModel.logicalConnective
            ruleConditionLeafModel.conditionPredicates shouldContain mockedRuleConditionPredicateModel
        }

        "Convert model to entity" {

            val mockedRuleConditionPredicateEntity = getTestRuleConditionPredicateEntity()
            every { ruleConditionPredicateMapper.modelToEntity(any()) } returns mockedRuleConditionPredicateEntity

            val ruleConditionLeafModel = getTestRuleConditionLeafModel()

            val ruleConditionLeafEntity = ruleConditionLeafMapper.modelToEntity(ruleConditionLeafModel)
            val expectedRuleConditionLeafEntity = getTestRuleConditionLeafEntity()

            ruleConditionLeafEntity shouldNotBe null
            ruleConditionLeafEntity.conditionId shouldBe expectedRuleConditionLeafEntity.conditionId
            ruleConditionLeafEntity.logicalConnective shouldBe expectedRuleConditionLeafEntity.logicalConnective
            ruleConditionLeafEntity.conditionPredicates shouldContain mockedRuleConditionPredicateEntity
        }

        "Convert entity to model" {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.entityToModel(any()) } returns mockedRuleConditionPredicateModel

            val ruleConditionLeafEntity = getTestRuleConditionLeafEntity()

            val ruleConditionLeafModel = ruleConditionLeafMapper.entityToModel(ruleConditionLeafEntity)
            val expectedRuleConditionLeafModel = getTestRuleConditionLeafModel()

            ruleConditionLeafModel shouldNotBe null
            ruleConditionLeafModel.conditionId shouldBe expectedRuleConditionLeafModel.conditionId
            ruleConditionLeafModel.logicalConnective shouldBe expectedRuleConditionLeafModel.logicalConnective
            ruleConditionLeafModel.conditionPredicates shouldContain mockedRuleConditionPredicateModel
        }
    }
}
