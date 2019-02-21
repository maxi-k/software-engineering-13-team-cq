package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import java.util.Date

@Entity
class VehicleStateDataTypeServiceEntity(

    @get: NotNull
    var dueDate: Date? = null,

    @get: NotNull
    var brakeFluid: String? = null,

    @get: NotNull
    var status: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataTypeEntity(dataTypeId), Serializable {

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VehicleStateDataTypeServiceEntity) return false
        if (!super.equals(other)) return false

        if (dueDate != other.dueDate) return false
        if (brakeFluid != other.brakeFluid) return false
        if (status != other.status) return false

        return true
    }

    // Autogenerated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (dueDate?.hashCode() ?: 0)
        result = 31 * result + (brakeFluid?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }
}
