package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.springframework.stereotype.Service
import de.unia.se.teamcq.rulemanagement.model.NotificationRule

/**
 * A service to manage sending Notifications for [NotificationRule]s.
 */
@Service
interface INotificationService {

    /**
     * Send scheduled Notifications
     *
     * @param notificationRule The [NotificationRule] with a ScheduledAggregator
     */
    fun sendNotificationForScheduledRule(notificationRule: NotificationRule)

    /**
     * Send non-scheduled Notifications
     *
     * @param notificationRule The [NotificationRule] with a ScheduledAggregator
     */
    fun sendNotificationForNonScheduledRule(notificationRule: NotificationRule)

    /**
     * Store data needed for future Notifications
     *
     * @param notificationData The [NotificationData], containing all required information
     */
    fun storeNotificationData(notificationData: NotificationData)
}
