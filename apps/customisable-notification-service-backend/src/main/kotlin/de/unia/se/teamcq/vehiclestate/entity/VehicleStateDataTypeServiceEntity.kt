package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.PrimaryKeyJoinColumn
import javax.validation.constraints.NotNull
import java.util.Date

@Entity
@PrimaryKeyJoinColumn(name = "dataTypeId")
data class VehicleStateDataTypeServiceEntity(

    @get: NotNull
    var dueDate: Date?,

    @get: NotNull
    var brakeFluid: String?,

    @get: NotNull
    var status: String?

) : VehicleStateDataTypeEntity(null), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
