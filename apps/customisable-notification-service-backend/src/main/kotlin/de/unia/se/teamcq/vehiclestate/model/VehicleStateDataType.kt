package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider

abstract class VehicleStateDataType(

    var dataTypeId: Long?

) : IPredicateFieldProvider
