package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import de.unia.se.teamcq.vehiclestate.model.IVehicleStateDataType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class VehicleStateService : IVehicleStateService {

    @Autowired
    private lateinit var vehicleStateRepository: IVehicleStateRepository

    @Autowired
    private lateinit var vehicleStateDataTypes: Set<IVehicleStateDataType>

    override fun importNewVehicleData() {
    }

    override fun getVehicleStateDataTypes(): Set<IVehicleStateDataType> {
        return vehicleStateDataTypes
    }
}
