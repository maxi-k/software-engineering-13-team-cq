package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeBattery(

    var charge: Double? = null,

    var voltage: Double? = null,

    var status: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? {
        return when (fieldName) {
            "charge" -> this.charge
            "voltage" -> this.voltage
            "status" -> this.status
            else -> super.retrieveFieldValue(fieldName)
        }
    }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Battery"
        val PREDICATE_FIELDS = mapOf(
            "charge" to PredicateField("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            "voltage" to PredicateField("voltage", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            "status" to PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
        )
    }
}
