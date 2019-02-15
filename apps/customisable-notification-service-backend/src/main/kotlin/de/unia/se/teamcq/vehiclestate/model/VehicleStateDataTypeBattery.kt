package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeBattery(

    var charge: Double?,

    var voltage: Double?,

    var status: String?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
        when (fieldName) {
            "charge" -> this.charge
            "voltage" -> this.voltage
            "status" -> this.status
            else -> super.retrieveFieldValue(fieldName)
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
