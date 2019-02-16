package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

// Constructor with (null)-default values for everything necessary for MapStruct
data class FleetReferenceDto(

    @get: NotNull
    var fleetId: String? = null

) : Serializable
