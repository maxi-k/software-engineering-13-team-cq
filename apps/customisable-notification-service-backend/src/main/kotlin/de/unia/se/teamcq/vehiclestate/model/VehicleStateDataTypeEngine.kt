package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeEngine(

    @get: NotNull
    var power: Int?,

    @get: NotNull
    var capacity: Int?,

    @get: NotNull
    var fuelType: String?,

    @get: NotNull
    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Engine"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("power", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("capacity", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("fuelType", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
