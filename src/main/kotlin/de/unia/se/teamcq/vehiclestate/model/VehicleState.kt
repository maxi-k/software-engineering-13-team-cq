package de.unia.se.teamcq.vehiclestate.model

import javax.validation.constraints.NotNull
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

data class VehicleState(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var eventId: Long? = 0,

    @get: NotNull
    var vehicleId: String?,

    @get: NotNull
    var kilometers: Int?,

    @get: NotNull
    var batteryCharge: Double?
) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
