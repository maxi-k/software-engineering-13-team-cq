package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.springframework.stereotype.Service
import de.unia.se.teamcq.rulemanagement.model.NotificationRule

/**
 * A service to manage sending Notifications for [NotificationRule]s.
 */
@Service
interface INotificationTextService {

    /**
     * Generate a html mail for a Notification
     *
     * @param notificationData The [NotificationData], containing all required information
     */
    fun getHtmlMailTextForNotification(notificationData: NotificationData): String

    /**
     * Generate a plain-text SMS for a Notification
     *
     * @param notificationData The [NotificationData], containing all required information
     */
    fun getSmsTextForNotification(notificationData: NotificationData): String
}
