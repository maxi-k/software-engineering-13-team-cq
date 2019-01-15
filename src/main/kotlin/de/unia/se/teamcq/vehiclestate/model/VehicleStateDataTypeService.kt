package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeService : IVehicleStateDataType {

    override val name: String = "Service"
}
