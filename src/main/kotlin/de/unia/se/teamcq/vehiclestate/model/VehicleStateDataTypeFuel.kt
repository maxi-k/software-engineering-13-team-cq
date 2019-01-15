package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeFuel : IVehicleStateDataType {

    override val name: String = "Fuel"
}
