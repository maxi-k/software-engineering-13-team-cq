package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestRuleConditionDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionModel
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateModel
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionCompositeDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
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
class RuleConditionMapperTest : StringSpec() {

    @MockK
    lateinit var ruleConditionPredicateMapper: IRuleConditionPredicateMapper

    @InjectMockKs
    lateinit var ruleConditionCompositeMapper: RuleConditionMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" {

            val mockedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()
            every { ruleConditionPredicateMapper.modelToDto(any()) } returns mockedRuleConditionPredicateDto

            val ruleConditionCompositeModel = getTestRuleConditionModel()

            val ruleConditionCompositeDto = ruleConditionCompositeMapper.modelToDto(ruleConditionCompositeModel) as RuleConditionCompositeDto
            val expectedRuleConditionCompositeDto = getTestRuleConditionDto() as RuleConditionCompositeDto

            ruleConditionCompositeDto shouldNotBe null
            ruleConditionCompositeDto.conditionId shouldBe expectedRuleConditionCompositeDto.conditionId
            ruleConditionCompositeDto.logicalConnective shouldBe expectedRuleConditionCompositeDto.logicalConnective
            ruleConditionCompositeDto.subConditions shouldContain mockedRuleConditionPredicateDto
        }

        "Convert dto to model" {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.dtoToModel(any()) } returns mockedRuleConditionPredicateModel

            val ruleConditionCompositeDto = getTestRuleConditionDto()

            val ruleConditionCompositeModel = ruleConditionCompositeMapper.dtoToModel(ruleConditionCompositeDto) as RuleConditionComposite
            val expectedRuleConditionCompositeModel = getTestRuleConditionModel() as RuleConditionComposite

            ruleConditionCompositeModel shouldNotBe null
            ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
            ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
            ruleConditionCompositeModel.subConditions shouldContain mockedRuleConditionPredicateModel
        }

        "Convert model to entity" {

            val mockedRuleConditionPredicateEntity = getTestRuleConditionPredicateEntity()
            every { ruleConditionPredicateMapper.modelToEntity(any()) } returns mockedRuleConditionPredicateEntity

            val ruleConditionCompositeModel = getTestRuleConditionModel()

            val ruleConditionCompositeEntity = ruleConditionCompositeMapper.modelToEntity(ruleConditionCompositeModel) as RuleConditionCompositeEntity
            val expectedRuleConditionCompositeEntity = getTestRuleConditionEntity() as RuleConditionCompositeEntity

            ruleConditionCompositeEntity shouldNotBe null
            ruleConditionCompositeEntity.conditionId shouldBe expectedRuleConditionCompositeEntity.conditionId
            ruleConditionCompositeEntity.logicalConnective shouldBe expectedRuleConditionCompositeEntity.logicalConnective
            ruleConditionCompositeEntity.subConditions shouldContain mockedRuleConditionPredicateEntity
        }

        "Convert entity to model" {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.entityToModel(any()) } returns mockedRuleConditionPredicateModel

            val ruleConditionCompositeEntity = getTestRuleConditionEntity()

            val ruleConditionCompositeModel = ruleConditionCompositeMapper.entityToModel(ruleConditionCompositeEntity) as RuleConditionComposite
            val expectedRuleConditionCompositeModel = getTestRuleConditionModel() as RuleConditionComposite

            ruleConditionCompositeModel shouldNotBe null
            ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
            ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
            ruleConditionCompositeModel.subConditions shouldContain mockedRuleConditionPredicateModel
        }
    }
}
