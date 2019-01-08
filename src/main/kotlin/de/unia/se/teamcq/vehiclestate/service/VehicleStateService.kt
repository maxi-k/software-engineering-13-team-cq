package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntityRepository
import org.springframework.stereotype.Component

@Component
class VehicleStateService(val vehicleStateEntityRepository: VehicleStateEntityRepository) : IVehicleStateService {

    override fun importNewVehicleData() {
    }
}
