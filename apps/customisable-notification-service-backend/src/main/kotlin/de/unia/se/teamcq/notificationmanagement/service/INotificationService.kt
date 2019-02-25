package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.springframework.stereotype.Service
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled

/**
 * A service to manage sending Notifications for [NotificationRule]s.
 */
@Service
interface INotificationService {

    /**
     * Send Notifications for a [NotificationRule] according to its [Aggregator]
     *
     * @param notificationRule The [NotificationRule] with a [AggregatorScheduled]
     */
    fun sendNotificationForRuleIfNecessary(notificationRule: NotificationRule)

    /**
     * Store data needed for future Notifications
     *
     * @param notificationData The [NotificationData], containing all required information
     */
    fun storeNotificationData(notificationData: NotificationData)
}
