package de.unia.se.teamcq.scheduling

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.stereotype.Service

/**
 * The central CRUD service that delegates tasks such as scheduling new [NotificationRule]s.
 */
@Service
interface IScheduledAggregatorRuleScheduler {

    /**
     * Schedule a [NotificationRule]
     *
     * Schedules a NotificationRule if it has a ScheduledAggregator.
     *
     * @param notificationRule The [NotificationRule] to schedule
     */
    fun scheduleNotificationRule(notificationRule: NotificationRule)
}
