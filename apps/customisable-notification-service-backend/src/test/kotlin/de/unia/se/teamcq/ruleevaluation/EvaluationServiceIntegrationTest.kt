package de.unia.se.teamcq.ruleevaluation

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.updateVehicleStateDataTypeField
import de.unia.se.teamcq.ruleevaluation.service.IEvaluationService
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
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
                val testVehicleState = TestUtils.getTestVehicleStateModel().updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>("Battery") {
                    it.charge = testCondition.comparisonValue?.toDouble()?.plus(1)
                }
                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false

                testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>("Battery") {
                    it.charge = testCondition.comparisonValue?.toDouble()
                }

                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe true
            }

            "Evaluate a composite NotificationRule correctly" {

                val predicateCondition = TestUtils.getTestRuleConditionPredicateModel()
                val testCondition = TestUtils.getTestRuleConditionCompositeModel().apply {
                    subConditions = listOf(predicateCondition)
                }
                val testVehicleState = TestUtils.getTestVehicleStateModel().updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>("Battery") {
                    it.charge = predicateCondition.comparisonValue?.toDouble()?.plus(1)
                }
                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false

                testVehicleState.updateVehicleStateDataTypeField<VehicleStateDataTypeBattery>("Battery") {
                    it.charge = predicateCondition.comparisonValue?.toDouble()
                }

                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe true
            }
        }
    }
}
