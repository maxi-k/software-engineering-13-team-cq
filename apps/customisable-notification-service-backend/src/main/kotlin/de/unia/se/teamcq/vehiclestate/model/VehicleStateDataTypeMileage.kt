package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeMileage(

    var current: Int? = null,

    var remaining: Int? = null,

    var reached: Int? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    override val predicateFieldProviderName: String = "Mileage"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("current", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("remaining", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("reached", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
    )
}
