package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class VehicleStateDataTypeFuelEntity(

    @get: NotNull
    var level: Double?,

    @get: NotNull
    var liters: Int?,

    @get: NotNull
    var range: Int?,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
