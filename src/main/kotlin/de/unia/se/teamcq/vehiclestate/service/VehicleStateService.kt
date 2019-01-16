package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class VehicleStateService : IVehicleStateService {

    @Autowired
    lateinit var vehicleStateRepository: IVehicleStateRepository

    override fun importNewVehicleData() {
    }
}
