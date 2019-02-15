package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Component

@Component
class EvaluationPredicateService : IEvaluationPredicateService {

    override fun <T : VehicleStateDataType> checkPredicate(ruleConditionPredicate: RuleConditionPredicate, vehicleStateDataType: T, predicateField: PredicateField<T, *>): Boolean {

        val comparisonType = ruleConditionPredicate.comparisonType
        val comparisonValue = ruleConditionPredicate.comparisonValue

        val fieldDataType = predicateField.dataType
        val dataValue = predicateField.fieldValueAccessor(vehicleStateDataType)

        return try {
            compareValues(comparisonType!!, fieldDataType!!, dataValue, comparisonValue!!)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // TODO: Use exception or false return value?
            false
        }
    }

    private inline fun <reified T, reified R : Iterable<T>, reified S : Comparable<T>> compareValues(
        comparisonType: ComparisonType,
        fieldDataType: FieldDataType,
        dataValue: T,
        comparisonValue: String
    ): Boolean {
        val convertedComparisonValue = fieldDataType.convertToFieldType(comparisonValue) as? T
                ?: throw IllegalArgumentException("ComparisonValue Conversion failed")

        return when (dataValue) {
            is R -> comparisonType.compare(dataValue, convertedComparisonValue)
            is S -> comparisonType.compare(dataValue, convertedComparisonValue)
            else -> throw IllegalArgumentException("Given comparisonValue could not be compared.")
        }
    }
}
