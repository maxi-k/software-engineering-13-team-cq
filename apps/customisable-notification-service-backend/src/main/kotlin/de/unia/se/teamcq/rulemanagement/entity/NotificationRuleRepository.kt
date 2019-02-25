package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.entity.IUserEntityRepository
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateMapper
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
interface INotificationRuleEntityRepository : JpaRepository<NotificationRuleEntity, Long>

@Component
@Transactional
class NotificationRuleRepository : INotificationRuleRepository {

    @Autowired
    private lateinit var notificationRuleEntityRepository: INotificationRuleEntityRepository

    @Autowired
    private lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    private lateinit var notificationRuleMapper: INotificationRuleMapper

    @Autowired
    private lateinit var vehicleStateMapper: IVehicleStateMapper

    @Autowired
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getAllNotificationRulesForUser(username: String): List<NotificationRule> {

        val userEntity = userEntityRepository.getOne(username)

        val notificationRuleEntities = userEntity.notificationRules

        return notificationRuleEntities.map { notificationRuleEntity ->
            notificationRuleMapper.entityToModel(notificationRuleEntity)
        }
    }

    override fun getNotificationRule(ruleId: Long): NotificationRule? {

        val notificationRuleEntity = notificationRuleEntityRepository.findById(ruleId).orElse(null)

        return notificationRuleEntity?.let { existingNotificationRuleEntity ->
            notificationRuleMapper.entityToModel(existingNotificationRuleEntity) }
    }

    override fun createNotificationRule(notificationRule: NotificationRule): NotificationRule? {
        // Use merge so that the persistence layer does not
        // try to create existing entities this references,
        // but instead uses the already existing ones.
        val notificationRuleEntityToSave = entityManager.merge(
                notificationRuleMapper.modelToEntity(notificationRule)
        )

        // Update last updated to be able to ignore older VehicleStates
        notificationRuleEntityToSave.lastUpdate = Timestamp(System.currentTimeMillis())

        // Create notificationRuleEntity first so it already has an ID
        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)
        // Add NotificationRuleEntity to UserEntity because the mapping is bidirectional
        userEntityRepository.getOne(notificationRule.owner!!.name!!)
                .addNotificationRuleEntity(savedNotificationRuleEntity)

        return notificationRuleMapper.entityToModel(savedNotificationRuleEntity)
    }

    override fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule? {
        // Use merge so that the persistence layer does not
        // try to create existing entities this references,
        // but instead uses the already existing ones.
        val notificationRuleEntityToSave = entityManager.merge(
                notificationRuleMapper.modelToEntity(notificationRule)
        )

        // Update last updated to be able to ignore older VehicleStates
        notificationRuleEntityToSave.lastUpdate = Timestamp(System.currentTimeMillis())

        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityToSave)

        return notificationRuleMapper.entityToModel(savedNotificationRuleEntity)
    }

    override fun deleteNotificationRule(ruleId: Long) {

        notificationRuleEntityRepository.deleteById(ruleId)
    }

    override fun getVehicleStateMatchesForRule(ruleId: Long): Set<VehicleState> {
        val notificationRuleEntity = notificationRuleEntityRepository.findById(ruleId).orElse(null)

        return notificationRuleEntity?.let { existingNotificationRuleEntity ->

            existingNotificationRuleEntity.matchedVehicleStatesNotYetSent?.map { vehicleStateEntity ->
                vehicleStateMapper.entityToModel(vehicleStateEntity)
            }?.toSet() ?: setOf()
        } ?: setOf()
    }

    override fun addVehicleStateMatchForRule(ruleId: Long, vehicleState: VehicleState) {
        val notificationRuleEntity = notificationRuleEntityRepository.findById(ruleId).orElse(null)
        val vehicleStateEntities = entityManager.merge(
                vehicleStateMapper.modelToEntity(vehicleState)
        )

        notificationRuleEntity?.let { existingNotificationRuleEntity ->

            existingNotificationRuleEntity.matchedVehicleStatesNotYetSent = existingNotificationRuleEntity
                    .matchedVehicleStatesNotYetSent?.plus(vehicleStateEntities)
                    ?: mutableSetOf(vehicleStateEntities)

            notificationRuleEntityRepository.save(existingNotificationRuleEntity)
        }
    }

    override fun deleteAllVehicleStateMatchesForRule(ruleId: Long) {
        val notificationRuleEntity = notificationRuleEntityRepository.findById(ruleId).orElse(null)

        notificationRuleEntity?.let { existingNotificationRuleEntity ->

            existingNotificationRuleEntity.matchedVehicleStatesNotYetSent = mutableSetOf()
            notificationRuleEntityRepository.save(existingNotificationRuleEntity)
        }
    }
}
