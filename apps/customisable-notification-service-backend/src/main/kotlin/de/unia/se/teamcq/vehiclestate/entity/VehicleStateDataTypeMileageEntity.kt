package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeMileageEntity(

    @get: NotNull
    var current: Int?,

    @get: NotNull
    var remaining: Int?,

    @get: NotNull
    var reached: Int?

) : VehicleStateDataTypeEntity() {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
