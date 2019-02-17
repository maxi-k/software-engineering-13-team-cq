package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
// Constructor with (null)-default values for everything necessary for MapStruct
class VehicleStateDataTypeBattery(

    var charge: Double? = null,

    var voltage: Double? = null,

    var status: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId) {

    val predicateFieldProviderName: String = "Battery"

    val predicateFields: List<PredicateField> = listOf(
            PredicateField("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("voltage", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
