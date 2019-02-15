package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@PrimaryKeyJoinColumn(name = "dataTypeId")
data class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int?,

    @get: NotNull
    @Fetch(value = FetchMode.SUBSELECT)
    var vins: List<String>?,

    @get: NotNull
    var calendarWeek: Int?

) : VehicleStateDataTypeEntity, Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
