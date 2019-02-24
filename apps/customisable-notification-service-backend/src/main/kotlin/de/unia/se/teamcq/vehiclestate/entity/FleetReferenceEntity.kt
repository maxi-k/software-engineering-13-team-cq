package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
data class FleetReferenceEntity(

    @Id
    var fleetId: String? = null,

    var carParkId: String? = null

) : Serializable
