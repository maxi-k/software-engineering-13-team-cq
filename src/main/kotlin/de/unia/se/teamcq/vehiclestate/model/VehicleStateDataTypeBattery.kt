package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
data class VehicleStateDataTypeBattery(

    var charge: Double?,

    var voltage: Double?,

    var status: String?

) : IVehicleStateDataType {

    // Necessary for MapStruct
    constructor() : this(null, null, null)

    override val name: String = "Battery"
}
