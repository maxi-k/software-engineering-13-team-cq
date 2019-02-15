package de.unia.se.teamcq.scheduling.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.stereotype.Service

/**
 * A service to schedule the data import, data processing and scheduled [NotificationRule]s .
 */
@Service
interface INotificationScheduler {

    /**
     * Schedule a [NotificationRule]
     *
     * Schedules a NotificationRule if it has a ScheduledAggregator.
     *
     * @param notificationRule The [NotificationRule] to schedule
     */
    fun scheduleNotificationRule(notificationRule: NotificationRule)

    /**
     * Schedule the VehicleState data import
     */
    fun scheduleVehicleStateDataImport()

    /**
     * Schedule the processing of all unprocessed imported VehicleState data
     */
    fun scheduleVehicleStateDataProcessing()
}
