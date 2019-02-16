package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NotificationTextService : INotificationTextService {

    override fun getTextForNotification(notificationData: NotificationData) {

        logger.info("Generating text for rule with ID {}", notificationData.notificationRule.ruleId)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationTextService::class.java)
    }
}
