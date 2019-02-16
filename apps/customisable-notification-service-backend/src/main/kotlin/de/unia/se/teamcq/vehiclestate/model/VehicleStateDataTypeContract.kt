package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery.Companion.PREDICATE_FIELDS
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeContract(

    var duePerWeek: Int? = null,

    var vins: List<String>? = mutableListOf(),

    var calendarWeek: Int? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "duePerWeek" -> this.duePerWeek
                "vins" -> this.vins
                "calendarWeek" -> this.calendarWeek
                else -> super.retrieveFieldValue(fieldName)
            }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Contract"
        val PREDICATE_FIELDS = mapOf(
            "duePerWeek" to PredicateField("duePerWeek", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            "vins" to PredicateField("vins", FieldDataType.STRING_LIST, EvaluationStrategies.LIST),
            "calendarWeek" to PredicateField("calendarWeek", FieldDataType.WEEK, EvaluationStrategies.NUMERIC)
        )
    }
}
