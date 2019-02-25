package de.unia.se.teamcq.vehiclestate.model

import java.sql.Timestamp
import javax.validation.constraints.NotNull

// Constructor with (null)-default values for everything necessary for MapStruct
data class VehicleState(

    @get: NotNull
    var stateId: Long? = null,

    @get: NotNull
    var vehicleReference: VehicleReference? = null,

    @get: NotNull
    var vehicleStateDataTypes: Set<VehicleStateDataType> = mutableSetOf(),

    var created: Timestamp? = null
)
