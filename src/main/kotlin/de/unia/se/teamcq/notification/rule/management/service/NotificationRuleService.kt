package de.unia.se.teamcq.notification.rule.management.service

import de.unia.se.teamcq.notification.rule.management.model.NotificationRule
import de.unia.se.teamcq.notification.rule.management.repository.NotificationRuleRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
interface NotificationRuleService {
    fun getNotificationRules(): List<NotificationRule>

    fun getNotificationRule(id: Long): NotificationRule

    // fun createNotificationRule(name: String, receivers: List<String>, description: String, fleets: List<Fleet>, formula: Formula, aggregator: Aggregator): NotificationRule

    // @Transactional
    // fun updateNotificationRule(id: Long, name: String, receivers: List<String>, description: String, fleets: List<Fleet>, formula: Formula, aggregator: Aggregator): NotificationRule

    fun deleteNotificationRule(id: Long): NotificationRule
}
