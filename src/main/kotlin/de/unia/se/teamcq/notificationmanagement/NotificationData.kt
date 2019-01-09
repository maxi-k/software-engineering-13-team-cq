package de.unia.se.teamcq.notificationmanagement

import de.unia.se.teamcq.rulemanagement.model.NotificationRule

class NotificationData(val trigger: String, val notificationRule: NotificationRule) : INotificationData {
    override fun getNoficationName(): String {
        return notificationRule.name!!
    }
}
