package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component
import java.util.Date

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeService(

    var dueDate: Date? = null,

    var brakeFluid: String? = null,

    var status: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    override val predicateFieldProviderName: String = PREDICATE_FIELD_PROVIDER_NAME

    override val predicateFields: Map<String, PredicateField> = PREDICATE_FIELDS

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "dueDate" -> this.dueDate
                "brakeFluid" -> this.brakeFluid
                "status" -> this.status
                else -> super.retrieveFieldValue(fieldName)
            }

    companion object {
        const val PREDICATE_FIELD_PROVIDER_NAME = "Service"
        val PREDICATE_FIELDS = mapOf(
                "dueDate" to PredicateField("dueDate", FieldDataType.DATE, EvaluationStrategies.NUMERIC),
                "brakeFluid" to PredicateField("brakeFluid", FieldDataType.TEXT, EvaluationStrategies.TEXT),
                "status" to PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
        )
    }
}
