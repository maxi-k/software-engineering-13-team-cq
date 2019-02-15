package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class VehicleStateDataTypeDto(

    @get: NotNull
    var dataTypeId: Long?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
