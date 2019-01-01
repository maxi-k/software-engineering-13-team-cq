package de.unia.se.teamcq.notification.management

interface Aggregator {

    fun processNotification (notification: NotificationData)
}
