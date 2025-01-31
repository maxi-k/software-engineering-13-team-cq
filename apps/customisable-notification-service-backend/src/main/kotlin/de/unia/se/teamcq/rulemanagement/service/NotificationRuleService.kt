package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.scheduling.service.INotificationScheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class NotificationRuleService : INotificationRuleService {

    @Autowired
    private lateinit var notificationRuleRepository: INotificationRuleRepository

    @Autowired
    private lateinit var notificationScheduler: INotificationScheduler

    override fun getNotificationRulesForUser(username: String): List<NotificationRule> {
        return notificationRuleRepository.getAllNotificationRulesForUser(username)
    }

    override fun getNotificationRule(ruleId: Long): NotificationRule? {
        return notificationRuleRepository.getNotificationRule(ruleId)
    }

    override fun createNotificationRule(username: String, notificationRule: NotificationRule): NotificationRule? {
        val createdNotificationRule = notificationRuleRepository.createNotificationRule(notificationRule)
        notificationScheduler.updateNotificationRuleScheduleAsNecessary(createdNotificationRule)
        return createdNotificationRule
    }

    override fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule? {
        val updatedNotificationRule = notificationRuleRepository.updateNotificationRule(notificationRule)
        notificationScheduler.updateNotificationRuleScheduleAsNecessary(updatedNotificationRule)
        return updatedNotificationRule
    }

    override fun deleteNotificationRule(ruleId: Long) {
        notificationRuleRepository.deleteNotificationRule(ruleId)
        notificationScheduler.deleteNotificationRuleSchedule(ruleId)
    }
}
