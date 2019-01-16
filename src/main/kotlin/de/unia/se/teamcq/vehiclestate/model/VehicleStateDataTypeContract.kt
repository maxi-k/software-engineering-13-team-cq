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
    constructor() : this(null, null, null, null)

    override val predicateFieldProviderName: String = "Contract"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("duePerWeek", FieldDataType.INTEGER, EvaluationStrategies.STANDARD),
            PredicateField("vins", FieldDataType.STRING_LIST, EvaluationStrategies.LIST),
            PredicateField("calendarWeek", FieldDataType.WEEK, EvaluationStrategies.STANDARD)
    )
}
