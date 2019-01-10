package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.stereotype.Service

@Service
interface INotificationRuleService {

    fun getNotificationRulesForUser(username: String): List<NotificationRule>

    fun getNotificationRule(ruleId: Long): NotificationRule?

    fun createNotificationRule(username: String, notificationRule: NotificationRule): NotificationRule?

    fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule?

    fun deleteNotificationRule(ruleId: Long)
}
