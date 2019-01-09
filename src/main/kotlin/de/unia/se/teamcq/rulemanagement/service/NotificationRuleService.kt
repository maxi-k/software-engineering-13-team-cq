package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationRuleService : INotificationRuleService {

    @Autowired
    lateinit var notificationRuleRepository: INotificationRuleRepository

    override fun getNotificationRulesForUser(username: String): List<NotificationRule> {
        return notificationRuleRepository.getAllNotificationRulesForUser(username)
    }

    override fun getNotificationRule(ruleId: Long): NotificationRule? {
        return notificationRuleRepository.getNotificationRule(ruleId)
    }

    @Transactional
    override fun createNotificationRule(username: String, notificationRule: NotificationRule): NotificationRule? {
        return notificationRuleRepository.createNotificationRule(notificationRule)
    }

    @Transactional
    override fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule? {
        return notificationRuleRepository.updateNotificationRule(notificationRule)
    }

    @Transactional
    override fun deleteNotificationRule(ruleId: Long) {
        notificationRuleRepository.deleteNotificationRule(ruleId)
    }
}
