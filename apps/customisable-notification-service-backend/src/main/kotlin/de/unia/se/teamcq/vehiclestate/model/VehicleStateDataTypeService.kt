package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.springframework.stereotype.Component
import java.util.Date

@Component
class VehicleStateDataTypeService(

    var dueDate: Date? = null,

    var brakeFluid: String? = null,

    var status: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataType(dataTypeId), IPredicateFieldProvider {

    override val predicateFieldProviderName: String = "Service"

    override val predicateFields: List<PredicateField> = listOf(
            PredicateField("dueDate", FieldDataType.DATE, EvaluationStrategies.NUMERIC),
            PredicateField("brakeFluid", FieldDataType.TEXT, EvaluationStrategies.TEXT),
            PredicateField("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
    )
}
