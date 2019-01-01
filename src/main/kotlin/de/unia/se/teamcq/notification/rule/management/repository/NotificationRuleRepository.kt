package de.unia.se.teamcq.notification.rule.management.repository

import de.unia.se.teamcq.notification.rule.management.model.NotificationRule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface NotificationRuleRepository : JpaRepository<NotificationRule, Long> {

    fun getNotificationRules(): List<NotificationRule>

    fun getNotificationRule(id: Long): NotificationRule

    // fun createNotificationRule(name: String, receivers: List<String>, description: String, fleets: List<Fleet>, formula: Formula, aggregator: Aggregator): NotificationRule

    @Transactional
    // fun updateNotificationRule(id: Long, name: String, receivers: List<String>, description: String, fleets: List<Fleet>, formula: Formula, aggregator: Aggregator): NotificationRule

    fun deleteNotificationRule(id: Long): NotificationRule
}
