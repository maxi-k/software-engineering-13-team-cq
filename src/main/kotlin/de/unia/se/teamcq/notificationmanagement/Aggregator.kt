package de.unia.se.teamcq.notificationmanagement

interface Aggregator {

    fun processNotification(notification: NotificationData)
}
