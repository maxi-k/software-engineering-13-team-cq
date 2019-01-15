package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable

data class VehicleStateDataTypeDto(

    var name: String?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
