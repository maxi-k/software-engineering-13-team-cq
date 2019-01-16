package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeEngine(

    var power: Int?,

    var capacity: Int?,

    var fuelType: String?

) : IVehicleStateDataType {

    // Necessary for MapStruct
    constructor() : this(null, null, null)

    override val name: String = "Engine"
}
