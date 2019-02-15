package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeFuel(

    @get: NotNull
    var level: Double?,

    @get: NotNull
    var liters: Int?,

    @get: NotNull
    var range: Int?,

    @get: NotNull
    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Fuel"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("level", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("liters", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("range", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
    )
}
