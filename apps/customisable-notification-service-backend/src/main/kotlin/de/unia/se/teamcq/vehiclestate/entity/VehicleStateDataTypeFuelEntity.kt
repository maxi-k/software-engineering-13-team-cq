package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeFuelEntity(

    @get: NotNull
    var level: Double? = null,

    @get: NotNull
    var liters: Int? = null,

    @get: NotNull
    var range: Int? = null

) : VehicleStateDataTypeEntity() 
