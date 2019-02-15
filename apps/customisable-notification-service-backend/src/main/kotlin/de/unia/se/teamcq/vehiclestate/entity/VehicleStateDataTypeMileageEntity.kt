package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@PrimaryKeyJoinColumn(name = "dataTypeId")
data class VehicleStateDataTypeMileageEntity(

    @get: NotNull
    var current: Int?,

    @get: NotNull
    var remaining: Int?,

    @get: NotNull
    var reached: Int?

) : VehicleStateDataTypeEntity, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
