package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NotificationService : INotificationService {

    @Autowired
    private lateinit var notificationTextService: INotificationTextService

    override fun sendNotificationForScheduledRule(notificationRule: NotificationRule) {
        // TODO: #112 Backend: Set up NotificationRule VehicleStateUpdate Processing
        logger.info("Sending notifications for scheduled rule with ID {}", notificationRule.ruleId)
    }

    override fun sendNotificationForNonScheduledRule(notificationRule: NotificationRule) {
        // TODO: #112 Backend: Set up NotificationRule VehicleStateUpdate Processing
        logger.info("Sending notifications for non-scheduled rule with ID {}", notificationRule.ruleId)
    }

    override fun storeNotificationData(notificationData: NotificationData) {
        // TODO: #112 Backend: Set up NotificationRule VehicleStateUpdate Processing
        logger.info("Storing notificationData for rule with ID {}", notificationData.notificationRule.ruleId)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationService::class.java)
    }
}
