package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeMileage(

    var current: Int?,

    var remaining: Int?,

    var reached: Int?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: List<PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "current" -> this.current
                "remaining" -> this.remaining
                "reachted" -> this.reached
                else -> super.retrieveFieldValue(fieldName)
            }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Mileage"
        val PREDICATE_FIELDS = listOf(
            PredicateField("current", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("remaining", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("reached", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
        )
    }
}
