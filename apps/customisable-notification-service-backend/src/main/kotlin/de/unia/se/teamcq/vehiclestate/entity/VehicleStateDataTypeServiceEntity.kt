package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class VehicleStateDataTypeServiceEntity(

    @get: NotNull
    var dueDate: Date?,

    @get: NotNull
    var brakeFluid: String?,

    @get: NotNull
    var status: String?,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
