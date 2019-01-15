package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeBattery : IVehicleStateDataType {

    override val name: String = "Battery"
}
