package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeEngineEntity(

    @get: NotNull
    var power: Int? = null,

    @get: NotNull
    var capacity: Int? = null,

    @get: NotNull
    var fuelType: String? = null

) : VehicleStateDataTypeEntity(), Serializable
