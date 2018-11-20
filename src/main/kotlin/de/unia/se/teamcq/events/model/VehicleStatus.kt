package de.unia.se.teamcq.events.model

import javax.validation.constraints.NotEmpty
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class VehicleStatus(
    @Id
    val id: String,

    @get: NotEmpty
    val kilometers: Int,

    @get: NotEmpty
    val batteryCharge: Float
) : Serializable