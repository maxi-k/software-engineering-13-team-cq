package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.entity.IUserEntityRepository
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface INotificationRuleEntityRepository : JpaRepository<NotificationRuleEntity, Long>

@Component
class NotificationRuleRepository : INotificationRuleRepository {

    @Autowired
    lateinit var notificationRuleEntityRepository: INotificationRuleEntityRepository

    @Autowired
    lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    lateinit var notificationRuleMapper: INotificationRuleMapper

    override fun getAllNotificationRulesForUser(username: String): List<NotificationRule> {
        val userEntity = userEntityRepository.getOne(username)

        Hibernate.initialize(userEntity.notificationRules)

        val notificationRuleEntities = userEntity.notificationRules.orEmpty()

        return notificationRuleEntities.map { notificationRuleEntity ->
            notificationRuleMapper.entityToModel(notificationRuleEntity)
        }
    }

    override fun getNotificationRule(ruleId: Long): NotificationRule? {
        val notificationRuleEntity = notificationRuleEntityRepository.findById(ruleId).get()

        return notificationRuleMapper.entityToModel(notificationRuleEntity)
    }

    override fun createNotificationRule(notificationRule: NotificationRule): NotificationRule? {
        val notificationRuleEntityToSave = notificationRuleMapper.modelToEntity(notificationRule)

        // Create notificationRuleEntity first so it already has an ID
        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)
        // Add NotificationRuleEntity to UserEntity because the mapping is bidirectional
        userEntityRepository.getOne(notificationRule.owner!!.name!!)
                .addNotificationRuleEntity(savedNotificationRuleEntity)

        return notificationRuleMapper.entityToModel(savedNotificationRuleEntity)
    }

    override fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule? {
        val notificationRuleEntityToSave = notificationRuleMapper.modelToEntity(notificationRule)

        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)

        return notificationRuleMapper.entityToModel(savedNotificationRuleEntity)
    }

    override fun deleteNotificationRule(ruleId: Long) {
        notificationRuleEntityRepository.deleteById(ruleId)
    }
}
