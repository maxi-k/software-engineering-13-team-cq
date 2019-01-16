package de.unia.se.teamcq.vehiclestate.entity

import javax.validation.constraints.NotNull
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class VehicleStateEntity(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var stateId: Long? = 0,

    @get: NotNull
    var vehicleId: String?

    // TODO: Add VehicleStateDataTypeEntities. See https://www.baeldung.com/hibernate-inheritance

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null)
}
