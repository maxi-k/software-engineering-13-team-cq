package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionDto
import de.unia.se.teamcq.TestUtils.getTestRuleConditionDtoWithGreaterDepth
import de.unia.se.teamcq.TestUtils.getTestRuleConditionEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionEntityWithGreaterDepth
import de.unia.se.teamcq.TestUtils.getTestRuleConditionModel
import de.unia.se.teamcq.TestUtils.getTestRuleConditionModelWithGreaterDepth
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
import io.kotlintest.shouldThrow
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
    lateinit var ruleConditionPredicateMapper: AbstractRuleConditionPredicateMapper

    @InjectMockKs
    lateinit var ruleConditionMapper: RuleConditionMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" should {

            val mockedRuleConditionPredicateDto = getTestRuleConditionPredicateDto()
            every { ruleConditionPredicateMapper.modelToDto(any()) } returns mockedRuleConditionPredicateDto

            "Convert condition model of depth 1 to dto" {

                val ruleConditionCompositeModel = getTestRuleConditionModel()

                val ruleConditionCompositeDto = ruleConditionMapper.modelToDto(ruleConditionCompositeModel) as RuleConditionCompositeDto
                val expectedRuleConditionCompositeDto = getTestRuleConditionDto() as RuleConditionCompositeDto

                ruleConditionCompositeDto shouldNotBe null
                ruleConditionCompositeDto.conditionId shouldBe expectedRuleConditionCompositeDto.conditionId
                ruleConditionCompositeDto.logicalConnective shouldBe expectedRuleConditionCompositeDto.logicalConnective
                ruleConditionCompositeDto.subConditions shouldContain mockedRuleConditionPredicateDto
            }

            "Convert condition model of depth greater than 1 to dto" {

                val ruleConditionCompositeModel = getTestRuleConditionModelWithGreaterDepth()

                val ruleConditionCompositeDto = ruleConditionMapper.modelToDto(ruleConditionCompositeModel) as RuleConditionCompositeDto
                val expectedRuleConditionCompositeDto = getTestRuleConditionDtoWithGreaterDepth() as RuleConditionCompositeDto

                ruleConditionCompositeDto shouldNotBe null
                ruleConditionCompositeDto.conditionId shouldBe expectedRuleConditionCompositeDto.conditionId
                ruleConditionCompositeDto.logicalConnective shouldBe expectedRuleConditionCompositeDto.logicalConnective

                val subConditionDto = ruleConditionCompositeDto.subConditions[0] as RuleConditionCompositeDto

                subConditionDto.subConditions shouldContain mockedRuleConditionPredicateDto
            }
        }

        "Convert dto to model" should {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.dtoToModel(any()) } returns mockedRuleConditionPredicateModel

            "Convert condition dto of depth 1 to model" {

                val ruleConditionCompositeDto = getTestRuleConditionDto()

                val ruleConditionCompositeModel = ruleConditionMapper.dtoToModel(ruleConditionCompositeDto) as RuleConditionComposite
                val expectedRuleConditionCompositeModel = getTestRuleConditionModel() as RuleConditionComposite

                ruleConditionCompositeModel shouldNotBe null
                ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
                ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
                ruleConditionCompositeModel.subConditions shouldContain mockedRuleConditionPredicateModel
            }

            "Convert condition dto of depth greater than 1 to model" {

                val ruleConditionCompositeDto = getTestRuleConditionDtoWithGreaterDepth()

                val ruleConditionCompositeModel = ruleConditionMapper.dtoToModel(ruleConditionCompositeDto) as RuleConditionComposite
                val expectedRuleConditionCompositeModel = getTestRuleConditionModelWithGreaterDepth() as RuleConditionComposite

                ruleConditionCompositeModel shouldNotBe null
                ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
                ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective

                val subConditionModel = ruleConditionCompositeModel.subConditions[0] as RuleConditionComposite

                subConditionModel.subConditions shouldContain mockedRuleConditionPredicateModel
            }

            "Throw exception without logicalConnective" {

                val ruleConditionCompositeDto = getTestRuleConditionCompositeDto().apply {
                    logicalConnective = null
                }

                shouldThrow<IllegalArgumentException> {
                    ruleConditionMapper.dtoToModel(ruleConditionCompositeDto)
                }
            }

            "Throw exception without subConditions" {

                val ruleConditionCompositeDto = getTestRuleConditionCompositeDto().apply {
                    subConditions = listOf()
                }

                shouldThrow<IllegalArgumentException> {
                    ruleConditionMapper.dtoToModel(ruleConditionCompositeDto)
                }
            }
        }

        "Convert model to entity" should {

            val mockedRuleConditionPredicateEntity = getTestRuleConditionPredicateEntity()
            every { ruleConditionPredicateMapper.modelToEntity(any()) } returns mockedRuleConditionPredicateEntity

            "Convert condition model of depth 1 to entity" {

                val ruleConditionCompositeModel = getTestRuleConditionModel()

                val ruleConditionCompositeEntity = ruleConditionMapper.modelToEntity(ruleConditionCompositeModel) as RuleConditionCompositeEntity
                val expectedRuleConditionCompositeEntity = getTestRuleConditionEntity() as RuleConditionCompositeEntity

                ruleConditionCompositeEntity shouldNotBe null
                ruleConditionCompositeEntity.conditionId shouldBe expectedRuleConditionCompositeEntity.conditionId
                ruleConditionCompositeEntity.logicalConnective shouldBe expectedRuleConditionCompositeEntity.logicalConnective
                ruleConditionCompositeEntity.subConditions shouldContain mockedRuleConditionPredicateEntity
            }

            "Convert condition model of depth greater than 1 to entity" {

                val ruleConditionCompositeModel = getTestRuleConditionModelWithGreaterDepth()

                val ruleConditionCompositeEntity = ruleConditionMapper.modelToEntity(ruleConditionCompositeModel) as RuleConditionCompositeEntity
                val expectedRuleConditionCompositeEntity = getTestRuleConditionEntityWithGreaterDepth() as RuleConditionCompositeEntity

                ruleConditionCompositeEntity shouldNotBe null
                ruleConditionCompositeEntity.conditionId shouldBe expectedRuleConditionCompositeEntity.conditionId
                ruleConditionCompositeEntity.logicalConnective shouldBe expectedRuleConditionCompositeEntity.logicalConnective

                val subConditionEntity = ruleConditionCompositeEntity.subConditions[0] as RuleConditionCompositeEntity

                subConditionEntity.subConditions shouldContain mockedRuleConditionPredicateEntity
            }
        }

        "Convert entity to model" should {

            val mockedRuleConditionPredicateModel = getTestRuleConditionPredicateModel()
            every { ruleConditionPredicateMapper.entityToModel(any()) } returns mockedRuleConditionPredicateModel

            "Convert condition entity of depth 1 to model" {

                val ruleConditionCompositeEntity = getTestRuleConditionEntity()

                val ruleConditionCompositeModel = ruleConditionMapper.entityToModel(ruleConditionCompositeEntity) as RuleConditionComposite
                val expectedRuleConditionCompositeModel = getTestRuleConditionModel() as RuleConditionComposite

                ruleConditionCompositeModel shouldNotBe null
                ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
                ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective
                ruleConditionCompositeModel.subConditions shouldContain mockedRuleConditionPredicateModel
            }

            "Convert condition entity of depth greater than 1 to model" {

                val ruleConditionCompositeEntity = getTestRuleConditionEntityWithGreaterDepth()

                val ruleConditionCompositeModel = ruleConditionMapper.entityToModel(ruleConditionCompositeEntity) as RuleConditionComposite
                val expectedRuleConditionCompositeModel = getTestRuleConditionModelWithGreaterDepth() as RuleConditionComposite

                ruleConditionCompositeModel shouldNotBe null
                ruleConditionCompositeModel.conditionId shouldBe expectedRuleConditionCompositeModel.conditionId
                ruleConditionCompositeModel.logicalConnective shouldBe expectedRuleConditionCompositeModel.logicalConnective

                val subConditionModel = ruleConditionCompositeModel.subConditions[0] as RuleConditionComposite

                subConditionModel.subConditions shouldContain mockedRuleConditionPredicateModel
            }
        }
    }
}
