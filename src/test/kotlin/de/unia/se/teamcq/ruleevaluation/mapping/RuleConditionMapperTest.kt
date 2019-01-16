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
import io.kotlintest.should
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
    lateinit var ruleConditionMapper: RuleConditionMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" should {

            "Convert condition model of depth 1 to dto" {

                val mockedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()
                every { ruleConditionPredicateMapper.modelToDto(any()) } returns mockedRuleConditionPredicateDto

                val ruleConditionCompositeModel = getTestRuleConditionModel()

                val ruleConditionCompositeDto = ruleConditionMapper.modelToDto(ruleConditionCompositeModel) as RuleConditionCompositeDto
                val expectedRuleConditionCompositeDto = getTestRuleConditionDto() as RuleConditionCompositeDto

                ruleConditionCompositeDto shouldNotBe null
                ruleConditionCompositeDto.conditionId shouldBe expectedRuleConditionCompositeDto.conditionId
                ruleConditionCompositeDto.logicalConnective shouldBe expectedRuleConditionCompositeDto.logicalConnective
                ruleConditionCompositeDto.subConditions shouldContain mockedRuleConditionPredicateDto
            }

            // TODO
        }

        "Convert dto to model" should {

            "Convert condition dto of depth 1 to model" {

                val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
                every { ruleConditionPredicateMapper.dtoToModel(any()) } returns mockedRuleConditionPredicateModel

                val ruleConditionCompositeDto = getTestRuleConditionDto()

                val ruleConditionCompositeModel = ruleConditionMapper.dtoToModel(ruleConditionCompositeDto) as RuleConditionComposite
                val expectedRuleConditionCompositeModel = getTestRuleConditionModel() as RuleConditionComposite

                ruleConditionCompositeModel shouldNotBe null
                ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
                ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
                ruleConditionCompositeModel.subConditions shouldContain mockedRuleConditionPredicateModel
            }

            // TODO
        }

        "Convert model to entity" should {

            "Convert condition model of depth 1 to entity" {

                val mockedRuleConditionPredicateEntity = getTestRuleConditionPredicateEntity()
                every { ruleConditionPredicateMapper.modelToEntity(any()) } returns mockedRuleConditionPredicateEntity

                val ruleConditionCompositeModel = getTestRuleConditionModel()

                val ruleConditionCompositeEntity = ruleConditionMapper.modelToEntity(ruleConditionCompositeModel) as RuleConditionCompositeEntity
                val expectedRuleConditionCompositeEntity = getTestRuleConditionEntity() as RuleConditionCompositeEntity

                ruleConditionCompositeEntity shouldNotBe null
                ruleConditionCompositeEntity.conditionId shouldBe expectedRuleConditionCompositeEntity.conditionId
                ruleConditionCompositeEntity.logicalConnective shouldBe expectedRuleConditionCompositeEntity.logicalConnective
                ruleConditionCompositeEntity.subConditions shouldContain mockedRuleConditionPredicateEntity
            }

            // TODO
        }

        "Convert entity to model" should {

            "Convert condition entity of depth 1 to model" {

                val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
                every { ruleConditionPredicateMapper.entityToModel(any()) } returns mockedRuleConditionPredicateModel

                val ruleConditionCompositeEntity = getTestRuleConditionEntity()

                val ruleConditionCompositeModel = ruleConditionMapper.entityToModel(ruleConditionCompositeEntity) as RuleConditionComposite
                val expectedRuleConditionCompositeModel = getTestRuleConditionModel() as RuleConditionComposite

                ruleConditionCompositeModel shouldNotBe null
                ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
                ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
                ruleConditionCompositeModel.subConditions shouldContain mockedRuleConditionPredicateModel
            }

            // TODO
        }
    }
}
