package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider

abstract class VehicleStateDataType(
    var dataTypeId: Long? = null
) : IPredicateFieldProvider {

    @Throws(IllegalArgumentException::class)
    open fun retrieveFieldValue(fieldName: String): Any? {
        throw IllegalArgumentException(
                "The field with the name $fieldName does not exist on ${javaClass.simpleName}."
        )
    }
}
