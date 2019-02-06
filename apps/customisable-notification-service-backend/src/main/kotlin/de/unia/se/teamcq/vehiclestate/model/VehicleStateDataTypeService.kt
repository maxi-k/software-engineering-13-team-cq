package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component
import java.util.Date

@Component
class VehicleStateDataTypeService(

    var dueDate: Date?,

    var brakeFluid: String?,

    var status: String?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Service"

    override val predicateFields: List<PredicateField<VehicleStateDataTypeService, Any>> = listOf(
            PredicateField("dueDate", FieldDataType.DATE, EvaluationStrategies.NUMERIC) { it.dueDate },
            PredicateField("brakeFluid", FieldDataType.TEXT, EvaluationStrategies.TEXT) { it.brakeFluid },
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT) { it.status }
    )
}
