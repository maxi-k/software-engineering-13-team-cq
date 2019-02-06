package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Component
import java.text.DateFormat

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
            val convertedComparisonValue = convertToFieldType(fieldDataType, comparisonValue) as? T
            return when (fieldDataType) {
                FieldDataType.TEXT, FieldDataType.INTEGER, FieldDataType.DECIMAL, FieldDataType.WEEK, FieldDataType.DATE -> {
                    if (dataValue is Comparable<*> && convertedComparisonValue is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        compareComparables(comparisonType,
                                dataValue as Comparable<T>,
                                convertedComparisonValue)
                    } else {
                        throw IllegalArgumentException()
                    }
                }
                FieldDataType.STRING_LIST -> {
                    if (dataValue is Iterable<*>) {
                        compareContainers(comparisonType, dataValue, convertedComparisonValue)
                    } else {
                        throw IllegalArgumentException()
                    }
                }
            }
        } catch (castException: ClassCastException) {
            throw IllegalArgumentException()
        }
    }

    private fun <R, T : Comparable<R>> compareComparables(
        comparisonType: ComparisonType,
        firstValue: T,
        secondValue: R
    ): Boolean =
        when (comparisonType) {
            ComparisonType.EQUAL_TO -> firstValue == secondValue
            ComparisonType.NOT_EQUAL_TO -> firstValue != secondValue
            ComparisonType.GREATER_THAN -> firstValue > secondValue
            ComparisonType.LESSER_THAN -> firstValue < secondValue
            ComparisonType.GREATER_THAN_OR_EQUAL_TO -> firstValue >= secondValue
            ComparisonType.LESSER_THAN_OR_EQUAL_TO -> firstValue <= secondValue
            // TODO: Throw exception? return false? create own exception type?
            else -> throw IllegalArgumentException()
        }

    private fun <T> compareContainers(
        comparisonType: ComparisonType,
        firstValue: Iterable<T>,
        secondValue: T
    ): Boolean =
            when (comparisonType) {
                ComparisonType.CONTAINED_IN -> firstValue.contains(secondValue)
                ComparisonType.NOT_CONTAINED_IN -> !firstValue.contains(secondValue)
                else -> throw IllegalArgumentException()
            }

    private fun convertToFieldType(fieldDataType: FieldDataType, value: String): Any =
            when (fieldDataType) {
                FieldDataType.TEXT, FieldDataType.STRING_LIST -> value
                FieldDataType.INTEGER, FieldDataType.WEEK -> value.toInt()
                FieldDataType.DECIMAL -> value.toFloat()
                // TODO: Think about how dates are saved,
                // and which format we need here
                FieldDataType.DATE -> DateFormat.getDateInstance().parse(value)
            }
}
