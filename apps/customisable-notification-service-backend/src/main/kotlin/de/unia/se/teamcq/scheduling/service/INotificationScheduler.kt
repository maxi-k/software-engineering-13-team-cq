package de.unia.se.teamcq.scheduling.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.stereotype.Service
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled

/**
 * A service to schedule the data import, data processing and scheduled [NotificationRule]s .
 */
@Service
interface INotificationScheduler {

    /**
     * Updates a [NotificationRule] schedule as necessary
     *
     * Schedules a NotificationRule if it has a [AggregatorScheduled].
     * Updates a schedule if it already has one from previous version.
     *
     * @param notificationRule The [NotificationRule] to schedule
     */
    fun updateNotificationRuleScheduleAsNecessary(notificationRule: NotificationRule?)

    /**
     * Delete a [NotificationRule] schedule if present
     */
    fun deleteNotificationRuleSchedule(ruleId: Long)

    /**
     * Schedule the VehicleState data import
     */
    fun scheduleVehicleStateDataImport()
}
