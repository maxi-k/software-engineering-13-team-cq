package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeMileage(

    var current: Int?,

    var remaining: Int?,

    var reached: Int?,

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
