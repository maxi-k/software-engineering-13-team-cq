package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.PrimaryKeyJoinColumn
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int?,

    @Fetch(value = FetchMode.SUBSELECT)
    var vins: List<String>?,

    @get: NotNull
    var calendarWeek: Int?

) : VehicleStateDataTypeEntity(), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, mutableListOf<String>(), null)
}
