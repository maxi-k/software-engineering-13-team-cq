package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.ManyToOne

@Entity
data class VehicleStateEntity(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var stateId: Long? = 0,

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = [CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.PERSIST])
    @JoinColumn(name = "fk_vehicle")
    var vehicleReference: VehicleReferenceEntity?,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var vehicleDataTypeBattery: VehicleDataTypeBattery?

    // TODO: Add other VehicleStateDataTypeEntities. See https://www.baeldung.com/hibernate-inheritance

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null)
}
