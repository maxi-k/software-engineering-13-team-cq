package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class VehicleStateDataTypeBatteryEntity(

    @get: NotNull
    var charge: Double? = null,

    @get: NotNull
    var voltage: Double? = null,

    @get: NotNull
    var status: String? = null,

    dataTypeId: Long? = null

) : VehicleStateDataTypeEntity(dataTypeId), Serializable {

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VehicleStateDataTypeBatteryEntity) return false
        if (!super.equals(other)) return false

        if (charge != other.charge) return false
        if (voltage != other.voltage) return false
        if (status != other.status) return false

        return true
    }

    // Autogenerated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (charge?.hashCode() ?: 0)
        result = 31 * result + (voltage?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }
}
