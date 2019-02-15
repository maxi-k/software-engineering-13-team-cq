package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class VehicleStateDataTypeMileageDto(

    @get: NotNull
    var current: Int?,

    @get: NotNull
    var remaining: Int?,

    @get: NotNull
    var reached: Int?

) : VehicleStateDataTypeDto, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
