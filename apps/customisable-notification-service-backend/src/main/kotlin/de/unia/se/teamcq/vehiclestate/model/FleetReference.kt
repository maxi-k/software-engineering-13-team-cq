package de.unia.se.teamcq.vehiclestate.model

// Constructor with (null)-default values for everything necessary for MapStruct
data class FleetReference(

    var fleetId: String? = null

) {

    fun fetchFleetData() {
        TODO()
    }
}
