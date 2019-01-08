package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntityRepository
import de.unia.se.teamcq.rulemanagement.mapping.NotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Component
class NotificationRuleService(val notificationRuleEntityRepository: NotificationRuleEntityRepository) : INotificationRuleService {

    @Autowired
    lateinit var notificationRuleMapper: NotificationRuleMapper

    override fun getNotificationRulesForUser(username: String): List<NotificationRule> {
        return notificationRuleEntityRepository.findAll().map { notificationRuleEntity ->
            notificationRuleMapper.entityToModel(notificationRuleEntity)
        }
    }

    override fun getNotificationRule(ruleId: Long): Optional<NotificationRule> {
        val notificationRuleEntity = notificationRuleEntityRepository.findById(ruleId).get()

        return Optional.of(notificationRuleMapper.entityToModel(notificationRuleEntity))
    }

    @Transactional
    override fun createNotificationRule(username: String, notificationRule: NotificationRule): Optional<NotificationRule> {
        val notificationRuleEntityToSave = notificationRuleMapper.modelToEntity(notificationRule)

        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)

        return Optional.of(notificationRuleMapper.entityToModel(savedNotificationRuleEntity))
    }

    @Transactional
    override fun updateNotificationRule(ruleId: Long, notificationRule: NotificationRule): Optional<NotificationRule> {
        val notificationRuleEntityToSave = notificationRuleMapper.modelToEntity(notificationRule)

        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)

        return Optional.of(notificationRuleMapper.entityToModel(savedNotificationRuleEntity))
    }

    @Transactional
    override fun deleteNotificationRule(ruleId: Long) {
        notificationRuleEntityRepository.deleteById(ruleId)
    }
}
