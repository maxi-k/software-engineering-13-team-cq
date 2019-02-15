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

    override val predicateFieldProviderName: String = "Battery"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("voltage", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
        when (fieldName) {
            "charge" -> this.charge
            "voltage" -> this.voltage
            "status" -> this.status
            else -> super.retrieveFieldValue(fieldName)
        }
}
