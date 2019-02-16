package de.unia.se.teamcq.ruleevaluation

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.updateVehicleStateDataTypeField
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import de.unia.se.teamcq.ruleevaluation.service.IEvaluationService
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import io.kotlintest.matchers.withClue
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EvaluationServiceIntegrationTest : StringSpec() {

    @Autowired
    private lateinit var evaluationService: IEvaluationService

    init {

        "The checkCondition method" should {

            "Evaluate a flat NotificationRule correctly" {

                val testCondition = TestUtils.getTestRuleConditionPredicateModel()
                val testVehicleState = TestUtils.getTestVehicleStateModel().updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                    it.charge = testCondition.comparisonValue?.toDouble()?.plus(1)
                }
                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false

                testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                    it.charge = testCondition.comparisonValue?.toDouble()
                }

                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe true
            }

            "Evaluate a composite NotificationRule correctly" {

                val predicateCondition = TestUtils.getTestRuleConditionPredicateModel()
                val testCondition = TestUtils.getTestRuleConditionCompositeModel().apply {
                    subConditions = listOf(predicateCondition)
                }
                val testVehicleState = TestUtils.getTestVehicleStateModel().updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                    it.charge = predicateCondition.comparisonValue?.toDouble()?.plus(1)
                }
                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false

                testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                    it.charge = predicateCondition.comparisonValue?.toDouble()
                }

                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe true
            }

            "Evaluate a complex composite NotificationRule correctly" {

                val batteryLowerBound = 0.2
                val batteryUpperBound = 0.7
                val predicateConditionLowerBound = TestUtils.getTestRuleConditionPredicateModel().apply {
                    comparisonType = ComparisonType.GREATER_THAN
                    comparisonValue = batteryLowerBound.toString()
                }
                val predicateConditionUpperBound = TestUtils.getTestRuleConditionPredicateModel().apply {
                    comparisonType = ComparisonType.LESS_THAN_OR_EQUAL_TO
                    comparisonValue = batteryUpperBound.toString()
                }
                val testCondition = TestUtils.getTestRuleConditionCompositeModel().apply {
                    logicalConnective = LogicalConnectiveType.ALL
                    subConditions = listOf(
                            predicateConditionLowerBound,
                            predicateConditionUpperBound
                    )
                }
                val testVehicleState = TestUtils.getTestVehicleStateModel()

                withClue("Values below the lower bound should be rejected") {
                    testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                        it.charge = batteryLowerBound - 0.01
                    }
                    evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false
                }

                withClue("Values above the upper bound should be rejected") {
                    testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                        it.charge = batteryUpperBound + 0.01
                    }
                    evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false
                }

                withClue("Values within the bound should be accepted (upper bound is inclusive)") {
                    testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>() {
                        it.charge = batteryUpperBound
                    }
                    evaluationService.checkCondition(testCondition, testVehicleState) shouldBe true
                }
            }
        }
    }
}
