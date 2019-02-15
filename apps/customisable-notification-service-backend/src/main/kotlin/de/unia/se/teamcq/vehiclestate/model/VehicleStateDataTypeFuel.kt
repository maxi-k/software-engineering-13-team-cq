package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeFuel(

    var level: Double?,

    var liters: Int?,

    var range: Int?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "level" -> this.level
                "liters" -> this.liters
                "range" -> this.range
                else -> super.retrieveFieldValue(fieldName)
            }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Fuel"
        val PREDICATE_FIELDS = mapOf(
            "level" to PredicateField("level", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            "liters" to PredicateField("liters", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            "range" to PredicateField("range", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
        )
    }
}
