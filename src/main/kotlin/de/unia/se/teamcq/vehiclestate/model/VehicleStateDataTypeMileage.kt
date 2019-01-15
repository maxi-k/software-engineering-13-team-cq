package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeMileage : IVehicleStateDataType {

    override val name: String = "Mileage"
}
