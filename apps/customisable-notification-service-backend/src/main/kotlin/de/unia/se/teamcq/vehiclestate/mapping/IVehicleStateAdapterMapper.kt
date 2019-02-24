package de.unia.se.teamcq.vehiclestate.mapping

import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Service

@Service
interface IVehicleStateAdapterMapper {

    fun dtoToModel(vehicle: Vehicle): VehicleState
}
