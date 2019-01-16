package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeEngine(

    var power: Int?,

    var capacity: Int?,

    var fuelType: String?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Engine"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("power", FieldDataType.INTEGER, EvaluationStrategies.STANDARD),
            PredicateField("capacity", FieldDataType.INTEGER, EvaluationStrategies.STANDARD),
            PredicateField("fuelType", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
