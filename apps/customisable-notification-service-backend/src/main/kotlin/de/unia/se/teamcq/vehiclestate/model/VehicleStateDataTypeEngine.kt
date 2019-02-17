package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeEngine(

    var power: Int? = null,

    var capacity: Int? = null,

    var fuelType: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

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
        val PREDICATE_FIELDS = mapOf(
                "power" to PredicateField("power", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
                "capacity" to PredicateField("capacity", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
                "fuelType" to PredicateField("fuelType", FieldDataType.TEXT, EvaluationStrategies.TEXT)
        )
    }
}
