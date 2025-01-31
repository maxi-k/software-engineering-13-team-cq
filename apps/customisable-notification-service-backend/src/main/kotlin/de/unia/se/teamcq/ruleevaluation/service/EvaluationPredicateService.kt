package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Component
import java.lang.ClassCastException

@Component
class EvaluationPredicateService : IEvaluationPredicateService {

    @Throws(IllegalArgumentException::class)
    override fun checkPredicate(
        ruleConditionPredicate: RuleConditionPredicate,
        vehicleStateDataType: VehicleStateDataType,
        predicateField: PredicateField
    ): Boolean {

        val comparisonType = ruleConditionPredicate.comparisonType
        val comparisonValue = ruleConditionPredicate.comparisonValue

        val fieldDataType = predicateField.dataType
        val dataValue = vehicleStateDataType.retrieveFieldValue(predicateField.fieldName!!)

        return compareValues(comparisonType!!, fieldDataType!!, dataValue, comparisonValue!!)
    }

    @Throws(IllegalArgumentException::class)
    private inline fun <reified T, reified R : Iterable<T>, reified S : Comparable<T>> compareValues(
        comparisonType: ComparisonType,
        fieldDataType: FieldDataType,
        actualVehicleStateValue: T,
        comparisonValue: String
    ): Boolean {
        return try {
            val convertedComparisonValue = fieldDataType.convertToFieldType(comparisonValue) as T
            when (actualVehicleStateValue) {
                is R -> comparisonType.compare(actualVehicleStateValue, convertedComparisonValue)
                is S -> comparisonType.compare(actualVehicleStateValue, convertedComparisonValue)
                else -> throw IllegalArgumentException(
                        "Given converted comparisonValue $convertedComparisonValue could not be " +
                                "compared to $actualVehicleStateValue using the comparison $comparisonType. " +
                                "This can happen if a declared PredicateField DataType mismatches with the " +
                                "type of the actual backing field."
                )
            }
        } catch (classCastException: ClassCastException) {
            throw IllegalArgumentException(
                    "ComparisonValue Conversion of $comparisonValue to $fieldDataType " +
                            "failed for comparison $comparisonType."
            )
        }
    }
}
