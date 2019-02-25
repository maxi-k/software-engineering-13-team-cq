package de.unia.se.teamcq.vehiclestate.model

// Constructor with (null)-default values for everything necessary for MapStruct
data class VehicleReference(

    var vin: String? = null,

    var fleetReference: FleetReference? = null,

    var brand: String? = null,

    var model: String? = null,

    var series: String? = null,

    var note: String? = null

)
