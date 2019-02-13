package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Component

@Component
class EvaluationPredicateService : IEvaluationPredicateService {

    override fun <T : VehicleStateDataType> checkPredicate(ruleConditionPredicate: RuleConditionPredicate, vehicleStateDataType: T, predicateField: PredicateField<T, Any>): Boolean {

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

    private inline fun <reified T> compareValues(
        comparisonType: ComparisonType,
        fieldDataType: FieldDataType,
        dataValue: T,
        comparisonValue: String
    ): Boolean {
        try {
            val convertedComparisonValue = fieldDataType.convertToFieldType(comparisonValue) as? T
            return when (fieldDataType) {
                FieldDataType.TEXT, FieldDataType.INTEGER, FieldDataType.DECIMAL, FieldDataType.WEEK, FieldDataType.DATE -> {
                    if (dataValue is Comparable<*> && convertedComparisonValue is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        comparisonType.compare(dataValue as Comparable<T>, convertedComparisonValue)
                    } else {
                        throw IllegalArgumentException()
                    }
                }
                FieldDataType.STRING_LIST -> {
                    if (dataValue is Iterable<*>) {
                        comparisonType.compare(dataValue, convertedComparisonValue)
                    } else {
                        throw IllegalArgumentException()
                    }
                }
            }
        } catch (castException: ClassCastException) {
            throw IllegalArgumentException()
        }
    }
}
