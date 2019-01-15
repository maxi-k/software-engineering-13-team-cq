package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeEngine : IVehicleStateDataType {

    override val name: String = "Engine"
}
