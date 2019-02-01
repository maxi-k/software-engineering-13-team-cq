package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class FleetReferenceEntity(

    @Id
    var fleetId: String?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
