package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeEngine(

    var power: Int? = null,

    var capacity: Int? = null,

    var fuelType: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    val predicateFieldProviderName: String = "Engine"

    val predicateFields: List<PredicateField> = listOf(
            PredicateField("power", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("capacity", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("fuelType", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
