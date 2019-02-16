package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeMileageEntity(

    @get: NotNull
    var current: Int? = null,

    @get: NotNull
    var remaining: Int? = null,

    @get: NotNull
    var reached: Int? = null

) : VehicleStateDataTypeEntity(), Serializable 
