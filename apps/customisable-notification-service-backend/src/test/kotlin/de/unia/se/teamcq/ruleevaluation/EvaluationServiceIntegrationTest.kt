package de.unia.se.teamcq.ruleevaluation

import de.unia.se.teamcq.TestUtils
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
                val testVehicleState = TestUtils.getTestVehicleStateModel()
                evaluationService.checkCondition(testCondition, testVehicleState) shouldBe false

                val testVehicleState2 = testVehicleState.apply {
                    this.vehicleStateDataTypes?.find {
                        it.predicateFieldProviderName == testCondition.providerName
                    }?.apply {
                        if (this is VehicleStateDataTypeBattery) {
                            this.charge = testCondition.comparisonValue?.toDouble()
                        }
                    }
                }

                evaluationService.checkCondition(testCondition, testVehicleState2) shouldBe true
            }
        }
    }
}
