package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

data class FleetReferenceDto(

    @get: NotNull
    var fleetId: String?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
