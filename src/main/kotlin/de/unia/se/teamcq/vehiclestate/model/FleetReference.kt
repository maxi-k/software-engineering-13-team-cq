package de.unia.se.teamcq.vehiclestate.model

data class FleetReference(

    var fleetId: String?

) {
    // Necessary for MapStruct
    constructor() : this(null)

    fun fetchFleetData() {
        TODO()
    }
}
