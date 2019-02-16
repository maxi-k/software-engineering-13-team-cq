package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeMileage(

    var current: Int? = null,

    var remaining: Int? = null,

    var reached: Int? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "current" -> this.current
                "remaining" -> this.remaining
                "reached" -> this.reached
                else -> super.retrieveFieldValue(fieldName)
            }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Mileage"
        val PREDICATE_FIELDS = mapOf(
            "current" to PredicateField("current", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            "remaining" to PredicateField("remaining", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            "reached" to PredicateField("reached", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
        )
    }
}
