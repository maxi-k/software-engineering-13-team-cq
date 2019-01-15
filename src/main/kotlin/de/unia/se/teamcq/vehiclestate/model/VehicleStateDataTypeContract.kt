package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeContract : IVehicleStateDataType {

    override val name: String = "Contract"
}
