package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class VehicleStateDataTypeContractDto(

    @get: NotNull
    var duePerWeek: Int?,

    @get: NotNull
    var vins: List<String>?,

    @get: NotNull
    var calendarWeek: Int?

) : VehicleStateDataTypeDto, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
