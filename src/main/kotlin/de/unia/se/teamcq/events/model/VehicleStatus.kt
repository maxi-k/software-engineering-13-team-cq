package de.unia.se.teamcq.events.model

import javax.validation.constraints.NotNull
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class VehicleStatus(
    @Id
    val vehicleId: String,

    @get: NotNull
    val kilometers: Int,

    @get: NotNull
    val batteryCharge: Float
) : Serializable