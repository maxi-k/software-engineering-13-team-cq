package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeBatteryEntity(

    @get: NotNull
    var charge: Double? = null,

    @get: NotNull
    var voltage: Double? = null,

    @get: NotNull
    var status: String? = null

) : VehicleStateDataTypeEntity(), Serializable