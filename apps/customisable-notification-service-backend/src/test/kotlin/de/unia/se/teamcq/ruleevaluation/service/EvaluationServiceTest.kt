package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class EvaluationServiceTest : StringSpec() {

    @MockK
    lateinit var predicateFieldContainer: PredicateFieldContainer

    @MockK
    lateinit var evaluationPredicateService: EvaluationPredicateService

    @InjectMockKs
    lateinit var evaluationService: EvaluationService

    init {
        MockKAnnotations.init(this)

        val falsePredicate = TestUtils.getTestRuleConditionPredicateModel()
        val truePredicate = TestUtils.getTestRuleConditionPredicateModel().apply {
            comparisonType = ComparisonType.NOT_EQUAL_TO
        }
        val testVehicleState = TestUtils.getTestVehicleStateModel()

        every {
            predicateFieldContainer.getPredicateFieldProviderByName(any())
        } returns TestUtils.getTestVehicleStateDataTypeBatteryModel()

        every { evaluationPredicateService.checkPredicate(falsePredicate, any(), any()) } returns false
        every { evaluationPredicateService.checkPredicate(truePredicate, any(), any()) } returns true

        "The CheckCondition method" should {

            "Return the value returned by the PredicateEvaluationService for simple conditions" {
                evaluationService.checkCondition(falsePredicate, testVehicleState) shouldBe false
                evaluationService.checkCondition(truePredicate, testVehicleState) shouldBe true
            }

            "Return true only if all subConditions match on a composite RuleCondition for the LogicalConnective ALL" {
                val compositeCondition = TestUtils.getTestRuleConditionCompositeModel().apply {
                    logicalConnective = LogicalConnectiveType.ALL
                    subConditions = listOf(
                            truePredicate,
                            truePredicate
                    )
                }
                evaluationService.checkCondition(compositeCondition, testVehicleState) shouldBe true

                compositeCondition.apply {
                    subConditions = listOf(
                            truePredicate,
                            falsePredicate
                    )
                }
                evaluationService.checkCondition(compositeCondition, testVehicleState) shouldBe false
            }

            "Return true if any subConditions match on a composite RuleCondition for the LogicalConnective ANY" {
                val compositeCondition = TestUtils.getTestRuleConditionCompositeModel().apply {
                    logicalConnective = LogicalConnectiveType.ANY
                    subConditions = listOf(
                            truePredicate,
                            falsePredicate
                    )
                }
                evaluationService.checkCondition(compositeCondition, testVehicleState) shouldBe true

                compositeCondition.apply {
                    subConditions = listOf(
                            falsePredicate,
                            falsePredicate
                    )
                }
                evaluationService.checkCondition(compositeCondition, testVehicleState) shouldBe false
            }

            "Return true if no subConditions match on a composite RuleCondition for the LogicalConnective NONE" {
                val compositeCondition = TestUtils.getTestRuleConditionCompositeModel().apply {
                    logicalConnective = LogicalConnectiveType.NONE
                    subConditions = listOf(
                            falsePredicate,
                            falsePredicate
                    )
                }
                evaluationService.checkCondition(compositeCondition, testVehicleState) shouldBe true

                compositeCondition.apply {
                    subConditions = listOf(
                            falsePredicate,
                            truePredicate
                    )
                }
                evaluationService.checkCondition(compositeCondition, testVehicleState) shouldBe false
            }
        }
    }
}
