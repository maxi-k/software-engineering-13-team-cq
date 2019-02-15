package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class VehicleStateDataTypeEngineDto(

    @get: NotNull
    var power: Int?,

    @get: NotNull
    var capacity: Int?,

    @get: NotNull
    var fuelType: String?

) : VehicleStateDataTypeDto, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
