package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeContract(

    @get: NotNull
    var duePerWeek: Int?,

    @get: NotNull
    var vins: List<String>?,

    @get: NotNull
    var calendarWeek: Int?,

    @get: NotNull
    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, mutableListOf<String>(), null, null)

    override val predicateFieldProviderName: String = "Contract"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("duePerWeek", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("vins", FieldDataType.STRING_LIST, EvaluationStrategies.LIST),
            PredicateField("calendarWeek", FieldDataType.WEEK, EvaluationStrategies.NUMERIC)
    )
}
