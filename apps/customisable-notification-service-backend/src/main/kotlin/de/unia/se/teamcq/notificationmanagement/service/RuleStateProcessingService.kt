package de.unia.se.teamcq.notificationmanagement.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RuleStateProcessingService : IRuleStateProcessingService {

    override fun processNewVehicleStates() {
        // TODO: #112 Backend: Set up NotificationRule VehicleStateUpdate Processing
        logger.info("Processing new VehicleStates")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RuleStateProcessingService::class.java)
    }
}
