package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import de.unia.se.teamcq.vehiclestate.model.VehicleState
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

        logger.info("Started importing new Vehicle States")
        val started = System.currentTimeMillis()

        val fleetReferences = vehicleStateRepository.getAllFleetReferences()
        val newVehicleStates = vssAdapter.getNewVehicleStates(fleetReferences)
        newVehicleStates.forEach { vehicleState ->
            vehicleStateRepository.createVehicleState(vehicleState)
        }

        val time = (System.currentTimeMillis() - started) / 1000.0
        logger.info("Finished importing new Vehicle States in {} s", time)
    }

    override fun getUnprocessedVehicleStateForRule(notificationRule: NotificationRule): List<VehicleState> {
        return vehicleStateRepository.getUnprocessedVehicleStateForRule(notificationRule)
                .filter { vehicleState -> notificationRule.affectedFleets.contains(
                        vehicleState.vehicleReference?.fleetReference)
                }
    }

    override fun markVehicleStateAsProcessedByRule(notificationRule: NotificationRule, vehicleStates: List<VehicleState>) {
        return vehicleStateRepository.markVehicleStateAsProcessedByRule(notificationRule, vehicleStates)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateService::class.java)
    }
}
