package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeContract(

    var duePerWeek: Int?,

    var vins: List<String>?,

    var calendarWeek: Int?,

    dataTypeId: Long?

) : VehicleStateDataType(dataTypeId) {

    // Necessary for MapStruct
    constructor() : this(null, mutableListOf<String>(), null, null)

    override val predicateFieldProviderName: String = "Contract"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("duePerWeek", FieldDataType.INTEGER, EvaluationStrategies.NUMERIC),
            PredicateField("vins", FieldDataType.STRING_LIST, EvaluationStrategies.LIST),
            PredicateField("calendarWeek", FieldDataType.WEEK, EvaluationStrategies.NUMERIC)
    )

    @Throws(IllegalArgumentException::class)
    override fun retrieveFieldValue(fieldName: String): Any? =
            when (fieldName) {
                "duePerWeek" -> this.duePerWeek
                "vins" -> this.vins
                "calendarWeek" -> this.calendarWeek
                else -> super.retrieveFieldValue(fieldName)
            }
}
