package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull

data class VehicleState(

    @get: NotNull
    var stateId: Long? = 0,

    @get: NotNull
    var vehicleReference: VehicleReference?,

    @get: NotNull
    var vehicleStateDataTypes: HashSet<VehicleStateDataType>?

) {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
