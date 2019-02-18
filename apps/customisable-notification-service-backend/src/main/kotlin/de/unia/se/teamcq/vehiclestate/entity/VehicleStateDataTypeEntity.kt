package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.*

@Entity
abstract class VehicleStateDataTypeEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long? = 0
)
