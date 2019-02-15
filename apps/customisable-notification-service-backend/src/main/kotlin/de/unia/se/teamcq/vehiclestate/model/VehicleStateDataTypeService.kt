package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component
import java.util.Date

@Component
class VehicleStateDataTypeService(

    @get: NotNull
    var dueDate: Date?,

    @get: NotNull
    var brakeFluid: String?,

    @get: NotNull
    var status: String?,

    @get: NotNull
    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Service"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("dueDate", FieldDataType.DATE, EvaluationStrategies.NUMERIC),
            PredicateField("brakeFluid", FieldDataType.TEXT, EvaluationStrategies.TEXT),
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
