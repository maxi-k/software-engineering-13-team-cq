package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeBattery(

    var charge: Double? = null,

    var voltage: Double? = null,

    var status: String? = null,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    override val predicateFieldProviderName: String = "Battery"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("voltage", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
