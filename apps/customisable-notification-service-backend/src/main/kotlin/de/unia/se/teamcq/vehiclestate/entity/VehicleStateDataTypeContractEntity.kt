package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int?,

    @Fetch(value = FetchMode.SUBSELECT)
    var vins: List<String>?,

    @get: NotNull
    var calendarWeek: Int?

) : VehicleStateDataTypeEntity() {
    // Necessary for MapStruct
    constructor() : this(null, mutableListOf<String>(), null)
}
