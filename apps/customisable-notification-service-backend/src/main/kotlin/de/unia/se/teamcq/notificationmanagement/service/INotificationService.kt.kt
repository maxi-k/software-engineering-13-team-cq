package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.springframework.stereotype.Service
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
<<<<<<< HEAD
=======
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
>>>>>>> master

/**
 * A service to manage sending Notifications for [NotificationRule]s.
 */
@Service
interface INotificationService {

    /**
     * Send scheduled Notifications
     *
<<<<<<< HEAD
     * @param notificationRule The [NotificationRule] with a ScheduledAggregator
=======
     * @param notificationRule The [NotificationRule] with a [AggregatorScheduled]
>>>>>>> master
     */
    fun sendNotificationForScheduledRule(notificationRule: NotificationRule)

    /**
     * Send non-scheduled Notifications
     *
<<<<<<< HEAD
     * @param notificationRule The [NotificationRule] with a ScheduledAggregator
=======
     * @param notificationRule The [NotificationRule] without a [AggregatorScheduled]
>>>>>>> master
     */
    fun sendNotificationForNonScheduledRule(notificationRule: NotificationRule)

    /**
     * Store data needed for future Notifications
     *
     * @param notificationData The [NotificationData], containing all required information
     */
    fun storeNotificationData(notificationData: NotificationData)
}
