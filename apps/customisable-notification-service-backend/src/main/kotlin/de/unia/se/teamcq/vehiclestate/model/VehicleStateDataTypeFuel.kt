package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeFuel(

    var level: Double? = null,

    var liters: Int? = null,

    var range: Int? = null,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    override val predicateFieldProviderName: String = "Fuel"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("level", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("liters", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("range", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC)
    )
}
