package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component
import java.util.Date

@Component
class VehicleStateDataTypeService(

    var dueDate: Date?,

    var brakeFluid: String?,

    var status: String?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Service"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("dueDate", FieldDataType.DATE, EvaluationStrategies.NUMERIC),
            PredicateField("brakeFluid", FieldDataType.TEXT, EvaluationStrategies.TEXT),
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "dueDate" -> this.dueDate
                "brakeFluid" -> this.brakeFluid
                "status" -> this.status
                else -> super.retrieveFieldValue(fieldName)
            }
}
