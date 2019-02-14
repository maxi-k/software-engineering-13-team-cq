package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.TestUtils
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

        "Evaluates RulePredicates correctly" {

            val predicateField = TestUtils.getTestPredicateFieldModel()
            val vehicleStateDataType = TestUtils.getTestVehicleStateDataTypeBatteryModel()
            val predicate = TestUtils.getTestRuleConditionPredicateModel().apply {
                comparisonValue = predicateField.fieldValueAccessor(vehicleStateDataType)
            }

            evaluationPredicateService.checkPredicate(
                    predicate,
                    vehicleStateDataType,
                    predicateField
            ) shouldBe false
        }
    }
}
