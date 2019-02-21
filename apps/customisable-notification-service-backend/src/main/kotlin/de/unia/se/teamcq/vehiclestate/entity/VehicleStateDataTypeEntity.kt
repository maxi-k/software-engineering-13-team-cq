package de.unia.se.teamcq.vehiclestate.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
abstract class VehicleStateDataTypeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long? = 0
)
