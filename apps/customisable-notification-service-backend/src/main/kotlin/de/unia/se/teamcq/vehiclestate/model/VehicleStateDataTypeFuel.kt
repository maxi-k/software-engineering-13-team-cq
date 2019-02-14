package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeFuel(

    var level: Double?,

    var liters: Int?,

    var range: Int?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Fuel"

    override val predicateFields: List<PredicateField<VehicleStateDataTypeFuel, Any>> = listOf(
            PredicateField("level", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC) { it.level },
            PredicateField("liters", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC) { it.liters },
            PredicateField("range", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC) { it.range }
    )
}
