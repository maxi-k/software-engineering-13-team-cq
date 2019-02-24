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

        val fleetReferences = vehicleStateRepository.getAllFleetReferences()

        val newVehicleStates = vssAdapter.getNewVehicleStates(fleetReferences)

        newVehicleStates.forEach { vehicleState ->
            vehicleStateRepository.createVehicleState(vehicleState)
        }

        logger.info("Importing VehicleStates successful!", newVehicleStates)
    }

    override fun getUnprocessedVehicleStateForRule(notificationRule: NotificationRule): List<VehicleState> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun markVehicleStateAsProcessedByRule(notificationRule: NotificationRule, vehicleStates: List<VehicleState>) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateService::class.java)
    }
}
