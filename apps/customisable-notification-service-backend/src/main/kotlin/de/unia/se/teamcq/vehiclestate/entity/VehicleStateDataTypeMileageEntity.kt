package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class VehicleStateDataTypeMileageEntity(

    @get: NotNull
    var current: Int?,

    @get: NotNull
    var remaining: Int?,

    @get: NotNull
    var reached: Int?,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
