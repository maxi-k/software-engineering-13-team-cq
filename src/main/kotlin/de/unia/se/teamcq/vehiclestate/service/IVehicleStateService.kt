package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.model.IVehicleStateDataType
import org.springframework.stereotype.Service

@Service
interface IVehicleStateService {

    fun importNewVehicleData()

    fun getVehicleStateDataTypes(): Set<IVehicleStateDataType>
}
