package de.unia.se.teamcq.vehiclestate.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

open class VehicleStateDataTypeDto(

    @get: NotNull
    var dataTypeId: Long?

)