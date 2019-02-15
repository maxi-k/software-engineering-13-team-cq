package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.TestUtils
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class EvaluationPredicateServiceTest : StringSpec() {

    @InjectMockKs
    lateinit var evaluationPredicateService: EvaluationPredicateService

    init {
        MockKAnnotations.init(this)

        "The CheckPredicate method" should {

            val predicateField = TestUtils.getTestPredicateFieldModel()
            val vehicleStateDataType = TestUtils.getTestVehicleStateDataTypeBatteryModel()

            "Evaluate RulePredicates correctly" {
                val predicate = TestUtils.getTestRuleConditionPredicateModel().apply {
                    comparisonValue = vehicleStateDataType.retrieveFieldValue(predicateField.fieldName!!).toString()
                }
                evaluationPredicateService.checkPredicate(
                        predicate,
                        vehicleStateDataType,
                        predicateField
                ) shouldBe true

                predicate.apply {
                    comparisonValue = "-$comparisonValue"
                }
                evaluationPredicateService.checkPredicate(
                        predicate,
                        vehicleStateDataType,
                        predicateField
                ) shouldBe false
            }
        }
    }
}
