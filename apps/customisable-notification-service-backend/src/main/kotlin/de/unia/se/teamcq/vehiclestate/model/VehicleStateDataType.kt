package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

open class VehicleStateDataType(
    
    @get: NotNull
    var dataTypeId: Long?
)
