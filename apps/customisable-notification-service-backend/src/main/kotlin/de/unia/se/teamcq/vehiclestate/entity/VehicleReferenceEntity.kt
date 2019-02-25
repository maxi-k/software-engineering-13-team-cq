package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
data class VehicleReferenceEntity(

    @Id
    var vin: String? = null,

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = [CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.PERSIST])
    @JoinColumn(name = "fk_fleet")
    var fleetReference: FleetReferenceEntity? = null,

    var brand: String? = null,

    var model: String? = null,

    var series: String? = null,

    var note: String? = null

) : Serializable
