package de.unia.se.teamcq.vehiclestate.model

import org.springframework.stereotype.Component
import java.util.Date

@Component
class VehicleStateDataTypeService(

    var dueDate: Date?,

    var brakeFluid: String?,

    var status: String?

) : IVehicleStateDataType {

    // Necessary for MapStruct
    constructor() : this(null, null, null)

    override val name: String = "Service"
}
