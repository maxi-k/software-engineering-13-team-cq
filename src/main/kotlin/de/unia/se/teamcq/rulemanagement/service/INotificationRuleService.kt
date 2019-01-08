package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
interface INotificationRuleService {
    fun getNotificationRulesForUser(username: String): List<NotificationRule>

    fun getNotificationRule(ruleId: Long): Optional<NotificationRule>

    @Transactional
    fun createNotificationRule(username: String, notificationRule: NotificationRule): Optional<NotificationRule>

    @Transactional
    fun updateNotificationRule(ruleId: Long, notificationRule: NotificationRule): Optional<NotificationRule>

    @Transactional
    fun deleteNotificationRule(ruleId: Long)
}
