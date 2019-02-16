package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeContract(

    var duePerWeek: Int? = null,

    var vins: Set<String>? = null,

    var calendarWeek: Int? = null,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    override val predicateFieldProviderName: String = "Contract"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("duePerWeek", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("vins", FieldDataType.STRING_LIST, EvaluationStrategies.LIST),
            PredicateField("calendarWeek", FieldDataType.WEEK, EvaluationStrategies.NUMERIC)
    )
}
