package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.CascadeType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
data class VehicleStateEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var stateId: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "fk_vehicle")
    var vehicleReference: VehicleReferenceEntity? = null,

    @ElementCollection
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL],
            targetEntity = VehicleStateDataTypeEntity::class, orphanRemoval = true)
    var vehicleStateDataTypes: Set<VehicleStateDataTypeEntity> = mutableSetOf(),

    var created: Timestamp? = null

) : Serializable
