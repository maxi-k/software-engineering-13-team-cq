package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.entity.IUserEntityRepository
import de.unia.se.teamcq.user.entity.IUserRepository
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
    lateinit var userRepository: IUserRepository

    @Autowired
    lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    lateinit var notificationRuleMapper: INotificationRuleMapper

    override fun getAllNotificationRulesForUser(username: String): List<NotificationRule> {
        val notificationRuleEntities = userEntityRepository.findById(username).orElseGet(null)
                ?.notificationRules.orEmpty()

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

        // FIXME: Clean up Entity Update Code and Locations next. We maybe want to rely on notificationRuleModel to be
        // FIXME: the one saved last and that all model attributes are present

        // Create User if necessary
        userRepository.getOrCreateUser(notificationRule.owner!!.name!!)
        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)
        savedNotificationRuleEntity.owner!!.addNotificationRuleEntity(savedNotificationRuleEntity)
        // Save User again with updated bidirectional mapping
        userRepository.createOrSaveUser(notificationRule.owner!!)

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
