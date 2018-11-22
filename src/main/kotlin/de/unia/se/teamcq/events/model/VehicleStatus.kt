package de.unia.se.teamcq.events.model

import javax.validation.constraints.NotNull
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class VehicleStatus(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val eventId: Long = 0,

    @get: NotNull
    val vehicleId: String,

    @get: NotNull
    val kilometers: Int,

    @get: NotNull
    val batteryCharge: Float
) : Serializable
