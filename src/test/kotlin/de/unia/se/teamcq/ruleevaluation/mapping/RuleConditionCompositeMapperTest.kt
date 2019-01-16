package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeModel
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
class RuleConditionCompositeMapperTest : StringSpec() {

    @MockK
    lateinit var ruleConditionPredicateMapper: IRuleConditionPredicateMapper

    @InjectMockKs
    lateinit var ruleConditionCompositeMapper: IRuleConditionCompositeMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" {

            val mockedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()
            every { ruleConditionPredicateMapper.modelToDto(any()) } returns mockedRuleConditionPredicateDto

            val ruleConditionCompositeModel = getTestRuleConditionCompositeModel()

            val ruleConditionCompositeDto = ruleConditionCompositeMapper.modelToDto(ruleConditionCompositeModel)
            val expectedRuleConditionCompositeDto = getTestRuleConditionCompositeDto()

            ruleConditionCompositeDto shouldNotBe null
            ruleConditionCompositeDto.conditionId shouldBe expectedRuleConditionCompositeDto.conditionId
            ruleConditionCompositeDto.logicalConnective shouldBe expectedRuleConditionCompositeDto.logicalConnective
            ruleConditionCompositeDto.conditionPredicates shouldContain mockedRuleConditionPredicateDto
        }

        "Convert dto to model" {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.dtoToModel(any()) } returns mockedRuleConditionPredicateModel

            val ruleConditionCompositeDto = getTestRuleConditionCompositeDto()

            val ruleConditionCompositeModel = ruleConditionCompositeMapper.dtoToModel(ruleConditionCompositeDto)
            val expectedRuleConditionCompositeModel = getTestRuleConditionCompositeModel()

            ruleConditionCompositeModel shouldNotBe null
            ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
            ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
            ruleConditionCompositeModel.conditionPredicates shouldContain mockedRuleConditionPredicateModel
        }

        "Convert model to entity" {

            val mockedRuleConditionPredicateEntity = getTestRuleConditionPredicateEntity()
            every { ruleConditionPredicateMapper.modelToEntity(any()) } returns mockedRuleConditionPredicateEntity

            val ruleConditionCompositeModel = getTestRuleConditionCompositeModel()

            val ruleConditionCompositeEntity = ruleConditionCompositeMapper.modelToEntity(ruleConditionCompositeModel)
            val expectedRuleConditionCompositeEntity = getTestRuleConditionCompositeEntity()

            ruleConditionCompositeEntity shouldNotBe null
            ruleConditionCompositeEntity.conditionId shouldBe expectedRuleConditionCompositeEntity.conditionId
            ruleConditionCompositeEntity.logicalConnective shouldBe expectedRuleConditionCompositeEntity.logicalConnective
            ruleConditionCompositeEntity.conditionPredicates shouldContain mockedRuleConditionPredicateEntity
        }

        "Convert entity to model" {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.entityToModel(any()) } returns mockedRuleConditionPredicateModel

            val ruleConditionCompositeEntity = getTestRuleConditionCompositeEntity()

            val ruleConditionCompositeModel = ruleConditionCompositeMapper.entityToModel(ruleConditionCompositeEntity)
            val expectedRuleConditionCompositeModel = getTestRuleConditionCompositeModel()

            ruleConditionCompositeModel shouldNotBe null
            ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
            ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
            ruleConditionCompositeModel.conditionPredicates shouldContain mockedRuleConditionPredicateModel
        }
    }
}
