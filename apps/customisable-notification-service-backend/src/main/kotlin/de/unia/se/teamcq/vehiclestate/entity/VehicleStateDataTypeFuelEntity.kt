package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@PrimaryKeyJoinColumn(name = "dataTypeId")
data class VehicleStateDataTypeFuelEntity(

    @get: NotNull
    var level: Double?,

    @get: NotNull
    var liters: Int?,

    @get: NotNull
    var range: Int?

) : VehicleStateDataTypeEntity, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
