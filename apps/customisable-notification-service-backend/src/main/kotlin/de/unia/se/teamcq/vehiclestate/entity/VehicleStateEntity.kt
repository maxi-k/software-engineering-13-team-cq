package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.*

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
data class VehicleStateEntity(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var stateId: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = [CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.PERSIST])
    @JoinColumn(name = "fk_vehicle")
    var vehicleReference: VehicleReferenceEntity? = null,

    @ElementCollection
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], targetEntity = VehicleStateDataTypeEntity::class,
            orphanRemoval = true)
    var vehicleStateDataTypes: Set<VehicleStateDataTypeEntity> = mutableSetOf()

) : Serializable
