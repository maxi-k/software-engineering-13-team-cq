package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NotificationService : INotificationService {

    override fun sendNotificationForScheduledRule(notificationRule: NotificationRule) {
        logger.info("Sending notifications for scheduled rule with ID {}", notificationRule.ruleId)
    }

    override fun sendNotificationForNonScheduledRule(notificationRule: NotificationRule) {
        logger.info("Sending notifications for non-scheduled rule with ID {}", notificationRule.ruleId)
    }

    override fun storeNotificationData(notificationData: NotificationData) {
        logger.info("Storing notificationData for rule with ID {}", notificationData.notificationRule.ruleId)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationService::class.java)
    }
}
