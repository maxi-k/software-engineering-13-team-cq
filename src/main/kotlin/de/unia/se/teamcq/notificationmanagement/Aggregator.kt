package de.unia.se.teamcq.notificationmanagement

interface Aggregator {

    fun processNotification(notificationData: INotificationData)
}
