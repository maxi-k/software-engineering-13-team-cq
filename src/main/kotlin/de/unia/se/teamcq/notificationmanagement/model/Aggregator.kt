package de.unia.se.teamcq.notificationmanagement.model

interface Aggregator {

    fun processNotification(notificationData: INotificationData)
}
