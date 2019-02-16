package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeEngineEntity(

    @get: NotNull
    var power: Int?,

    @get: NotNull
    var capacity: Int?,

    @get: NotNull
    var fuelType: String?

) : VehicleStateDataTypeEntity() {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
