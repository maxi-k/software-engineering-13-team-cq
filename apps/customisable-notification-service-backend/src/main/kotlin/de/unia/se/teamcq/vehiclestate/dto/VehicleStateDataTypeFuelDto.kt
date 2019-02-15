package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class VehicleStateDataTypeFuelDto(

    @get: NotNull
    var level: Double?,

    @get: NotNull
    var liters: Int?,

    @get: NotNull
    var range: Int?

) : VehicleStateDataTypeDto, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
