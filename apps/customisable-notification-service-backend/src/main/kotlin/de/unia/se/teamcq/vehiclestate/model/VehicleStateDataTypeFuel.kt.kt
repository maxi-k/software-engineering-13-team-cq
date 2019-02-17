package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeFuel(

    var level: Double? = null,

    var liters: Int? = null,

    var range: Int? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

<<<<<<< HEAD
    override val predicateFieldProviderName: String = "Fuel"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("level", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("liters", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("range", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
    )
=======
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
>>>>>>> master
}
