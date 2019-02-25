package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.time.format.DateTimeParseException

@ContextConfiguration(classes = [TestConfiguration::class])
class EvaluationPredicateServiceTest : StringSpec() {

    @InjectMockKs
    lateinit var evaluationPredicateService: EvaluationPredicateService

    init {
        MockKAnnotations.init(this)

        "The CheckPredicate method" should {

            val predicateField = TestUtils.getTestPredicateFieldModel()
            val vehicleStateDataType = TestUtils.getTestVehicleStateDataTypeBatteryModel()
            val predicate = TestUtils.getTestRuleConditionPredicateModel().apply {
                comparisonValue = vehicleStateDataType.retrieveFieldValue(predicateField.fieldName!!).toString()
            }

            "Evaluate RulePredicates for Comparable values correctly" {
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

            // TODO: This is how Iterable-Datatypes can be handled using our Evaluatiom mechanism.
            // TODO: This example can be removed if it won't be needed and/or
            // TODO: a Iterable-Datatype got added
            /* "Evaluate RulePredicates for Iterable values correctly" {
                val contractVehicleStateDataType = getTestVehicleStateDataTypeContractModel()
                val stringListPredicate = TestUtils.getTestRuleConditionPredicateModel().apply {
                    comparisonType = ComparisonType.CONTAINED_IN
                    comparisonValue = contractVehicleStateDataType.vins?.first()
                }
                val vinPredicateField = contractVehicleStateDataType.predicateFields["vins"]!!

                evaluationPredicateService.checkPredicate(
                        stringListPredicate,
                        contractVehicleStateDataType,
                        vinPredicateField
                ) shouldBe true

                stringListPredicate.apply {
                    comparisonValue += "nonExistentVin"
                }

                evaluationPredicateService.checkPredicate(
                        stringListPredicate,
                        contractVehicleStateDataType,
                        vinPredicateField
                ) shouldBe false
            } */

            "Throws exceptions when comparing using the wrong comparison type" {
                val nonApplicablePredicate = TestUtils.getTestRuleConditionPredicateModel().apply {
                    comparisonType = ComparisonType.CONTAINED_IN
                }
                shouldThrow<IllegalArgumentException> {
                    evaluationPredicateService.checkPredicate(
                            nonApplicablePredicate,
                            vehicleStateDataType,
                            predicateField
                    )
                }
            }

            "Throws exceptions when using a non-convertible field type" {
                val nonApplicablePredicateField = TestUtils.getTestPredicateFieldModel().apply {
                    dataType = FieldDataType.DATE
                }
                shouldThrow<DateTimeParseException> {
                    evaluationPredicateService.checkPredicate(
                            predicate,
                            vehicleStateDataType,
                            nonApplicablePredicateField
                    )
                }
            }

            "Throws exceptions when using a different field type" {
                val nonApplicablePredicateField = TestUtils.getTestPredicateFieldModel().apply {
                    dataType = FieldDataType.TEXT
                }
                shouldThrow<IllegalArgumentException> {
                    evaluationPredicateService.checkPredicate(
                            predicate,
                            vehicleStateDataType,
                            nonApplicablePredicateField
                    )
                }
            }

            "Throws exceptions for non-comparable fields" {
                val newVehicleStateDataType = object : VehicleStateDataType(0) {
                    override val predicateFieldProviderName: String = "newVehicleStateDataType"
                    override val predicateFields: Map<String, PredicateField> = emptyMap()
                    override fun retrieveFieldValue(fieldName: String): Any? {
                        return StringBuffer()
                    }
                }

                shouldThrow<IllegalArgumentException> {
                    evaluationPredicateService.checkPredicate(
                            predicate,
                            newVehicleStateDataType,
                            predicateField
                    )
                }
            }
        }
    }
}
