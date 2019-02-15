package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeMileage(

    @get: NotNull
    var current: Int?,

    @get: NotNull
    var remaining: Int?,

    @get: NotNull
    var reached: Int?,

    @get: NotNull
    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Mileage"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("current", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("remaining", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("reached", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
    )
}
