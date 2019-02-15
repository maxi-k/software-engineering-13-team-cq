package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@PrimaryKeyJoinColumn(name = "dataTypeId")
data class VehicleStateDataTypeBatteryEntity(

    @get: NotNull
    var charge: Double?,

    @get: NotNull
    var voltage: Double?,

    @get: NotNull
    var status: String?

) : VehicleStateDataTypeEntity, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
