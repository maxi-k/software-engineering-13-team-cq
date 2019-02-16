package de.unia.se.teamcq.notificationmanagement.service

import org.springframework.stereotype.Service
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.model.VehicleState

/**
 * A service to let [NotificationRule]s process all unprocessed [VehicleState]s.
 */
@Service
interface IRuleStateProcessingService {

    /**
     * Process all new, unprocessed [VehicleState]s
     */
    fun processNewVehicleStates()
}
