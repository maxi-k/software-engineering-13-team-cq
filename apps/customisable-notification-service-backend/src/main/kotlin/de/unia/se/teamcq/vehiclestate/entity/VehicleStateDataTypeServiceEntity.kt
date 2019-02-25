package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity

@Entity
class VehicleStateDataTypeServiceEntity(

    var status: String? = null,
    var dueDate: String? = null,
    var remainingDays: Int? = null,
    var remainingMileage: Int? = null,
    dataTypeId: Long? = null

) : VehicleStateDataTypeEntity(dataTypeId), Serializable {

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VehicleStateDataTypeServiceEntity) return false
        if (!super.equals(other)) return false

        if (status != other.status) return false
        if (dueDate != other.dueDate) return false
        if (remainingDays != other.remainingDays) return false
        if (remainingMileage != other.remainingMileage) return false

        return true
    }

    // Autogenerated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (dueDate?.hashCode() ?: 0)
        result = 31 * result + (remainingDays ?: 0)
        result = 31 * result + (remainingMileage ?: 0)
        return result
    }


}
