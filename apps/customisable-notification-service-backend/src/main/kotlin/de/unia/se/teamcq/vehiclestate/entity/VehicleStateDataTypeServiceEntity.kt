package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import java.util.Date

@Entity
data class VehicleStateDataTypeServiceEntity(

    @get: NotNull
    var dueDate: Date?,

    @get: NotNull
    var brakeFluid: String?,

    @get: NotNull
    var status: String?

) : VehicleStateDataTypeEntity() {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
