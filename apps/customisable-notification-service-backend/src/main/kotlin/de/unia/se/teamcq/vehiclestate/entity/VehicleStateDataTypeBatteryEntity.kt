package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeBatteryEntity(

    @get: NotNull
    var charge: Double?,

    @get: NotNull
    var voltage: Double?,

    @get: NotNull
    var status: String?

) : VehicleStateDataTypeEntity() {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}