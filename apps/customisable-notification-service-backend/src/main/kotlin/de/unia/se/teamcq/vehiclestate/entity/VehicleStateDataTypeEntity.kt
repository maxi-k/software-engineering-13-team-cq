package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class VehicleStateDataTypeEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long? = 0
) 