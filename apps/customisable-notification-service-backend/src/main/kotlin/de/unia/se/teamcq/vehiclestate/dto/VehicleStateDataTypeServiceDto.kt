package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class VehicleStateDataTypeServiceDto(

   @get: NotNull
    var dueDate: Date?,

    @get: NotNull
    var brakeFluid: String?,

    @get: NotNull
    var status: String?

) : VehicleStateDataTypeDto, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
