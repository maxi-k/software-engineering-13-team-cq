package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeContract(

    var duePerWeek: Int?,

    var vins: List<String>?,

    var calendarWeek: Int?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, mutableListOf<String>(), null, null)

    override val predicateFieldProviderName: String = "Contract"

    override val predicateFields: List<PredicateField<VehicleStateDataTypeContract, Any>> = listOf(
            PredicateField("duePerWeek", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC) { it.duePerWeek },
            PredicateField("vins", FieldDataType.STRING_LIST, EvaluationStrategies.LIST) { it.vins },
            PredicateField("calendarWeek", FieldDataType.WEEK, EvaluationStrategies.NUMERIC) { it.calendarWeek }
    )
}
