package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.mockito.ArgumentMatchers.any
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class EvaluationPredicateServiceTest : StringSpec() {

    @MockK
    lateinit var predicateFieldContainer: IPredicateFieldContainer

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

            every {
                predicateFieldContainer.getPredicateFieldByProviderAndName(any(), any())
            } returns vehicleStateDataType.predicateFields.find {
                it.fieldName == predicateField.fieldName
            }

            evaluationPredicateService.checkPredicate(
                    predicate,
                    vehicleStateDataType,
                    predicateField
            ) shouldBe false
        }
    }
}
