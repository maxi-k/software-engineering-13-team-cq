package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import java.util.Date

@Entity
data class VehicleStateDataTypeServiceEntity(

    @get: NotNull
    var dueDate: Date? = null,

    @get: NotNull
    var brakeFluid: String? = null,

    @get: NotNull
    var status: String? = null

) : VehicleStateDataTypeEntity() 