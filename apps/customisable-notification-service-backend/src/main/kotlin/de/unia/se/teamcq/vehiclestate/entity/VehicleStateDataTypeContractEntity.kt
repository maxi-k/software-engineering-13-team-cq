package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int?,

    @get: NotNull
    @Fetch(value = FetchMode.SUBSELECT)
    var vins: List<String>?,

    @get: NotNull
    var calendarWeek: Int?,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var dataTypeId: Long?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
