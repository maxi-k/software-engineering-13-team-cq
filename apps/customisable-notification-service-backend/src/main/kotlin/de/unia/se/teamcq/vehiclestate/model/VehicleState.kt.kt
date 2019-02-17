package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

// Constructor with (null)-default values for everything necessary for MapStruct
data class VehicleState(

    @get: NotNull
    var stateId: Long? = null,

    @get: NotNull
    var vehicleReference: VehicleReference? = null,

    @get: NotNull
<<<<<<< HEAD
    var vehicleStateDataTypes: Set<VehicleStateDataType>? = hashSetOf<VehicleStateDataType>()

) 
=======
    var vehicleStateDataTypes: Set<VehicleStateDataType> = mutableSetOf()

)
>>>>>>> master
