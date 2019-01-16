package de.unia.se.teamcq.notificationmanagement.model

import org.springframework.scheduling.support.CronTrigger

class ScheduledAggregator(val notificationCronTrigger: CronTrigger) : Aggregator {

    override fun processNotification(notificationData: INotificationData) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
