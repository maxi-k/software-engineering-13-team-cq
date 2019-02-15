package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeEngine(

    var power: Int?,

    var capacity: Int?,

    var fuelType: String?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: List<PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "power" -> this.power
                "capacity" -> this.capacity
                "fuelType" -> this.fuelType
                else -> super.retrieveFieldValue(fieldName)
            }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Engine"
        val PREDICATE_FIELDS = listOf(
            PredicateField("power", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("capacity", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("fuelType", FieldDataType.TEXT, EvaluationStrategies.TEXT)
        )
    }
}
