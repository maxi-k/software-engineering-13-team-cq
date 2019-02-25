package de.unia.se.teamcq.vehiclestate.mapping

import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Service
import java.lang.NullPointerException

@Service
interface IVehicleStateAdapterMapper {

    @Throws(NullPointerException::class)
    fun dtoToModel(vehicle: Vehicle): VehicleState
}
