package de.unia.se.teamcq.vehiclestate.model

data class VehicleReference(

    var vin: String?,

    var fleetReference: FleetReference?

) {
    // Necessary for MapStruct
    constructor() : this(null, null)

    fun fetchVehicleData() {
        TODO()
    }
}
