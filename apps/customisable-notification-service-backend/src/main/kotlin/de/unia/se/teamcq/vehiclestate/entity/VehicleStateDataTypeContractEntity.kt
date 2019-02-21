package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int? = null,

    @ElementCollection
    @Column(name = "vin")
    var vins: Set<String>? = mutableSetOf(),

    @get: NotNull
    var calendarWeek: Int? = null,

    dataTypeId: Long? = null

) : VehicleStateDataTypeEntity(dataTypeId), Serializable {

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VehicleStateDataTypeContractEntity

        if (duePerWeek != other.duePerWeek) return false
        if (vins != other.vins) return false
        if (calendarWeek != other.calendarWeek) return false

        return true
    }

    // Autogenerated
    override fun hashCode(): Int {
        var result = duePerWeek ?: 0
        result = 31 * result + (vins?.hashCode() ?: 0)
        result = 31 * result + (calendarWeek ?: 0)
        return result
    }
}
