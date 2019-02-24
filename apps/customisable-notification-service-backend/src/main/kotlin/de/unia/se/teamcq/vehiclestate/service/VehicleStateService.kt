package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException

@Component
@Transactional
class VehicleStateService : IVehicleStateService {

    @Autowired
    private lateinit var vssAdapter: IVssAdapter

    @Autowired
    private lateinit var vehicleStateRepository: IVehicleStateRepository

    @Throws(RestClientException::class, NullPointerException::class)
    override fun importNewVehicleData() {

        val newVehicleStates = vssAdapter.getNewVehicleStates(setOf(
                FleetReference(
                    "cccccccc-0000-ffff-0000-000000000099",
                    "cccccccc-0000-cccc-0000-000000000099"
                )
        ))
        newVehicleStates.forEach { vehicleState ->
            vehicleStateRepository.createVehicleState(vehicleState)
        }

        logger.info("Importing VehicleStates successful!", newVehicleStates)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateService::class.java)
    }
}
