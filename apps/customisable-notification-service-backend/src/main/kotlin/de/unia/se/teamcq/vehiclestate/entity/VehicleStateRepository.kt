package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleEntityRepository
import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.mapping.AbstractFleetReferenceMapper
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateMapper
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
interface IVehicleStateEntityRepository : JpaRepository<VehicleStateEntity, Long>

@Repository
interface IFleetReferenceEntityRepository : JpaRepository<FleetReferenceEntity, Long>

@Component
@Transactional
class VehicleStateRepository : IVehicleStateRepository {

    @Autowired
    private lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    @Autowired
    private lateinit var fleetReferenceEntityEntityRepository: IFleetReferenceEntityRepository

    @Autowired
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var vehicleStateMapper: IVehicleStateMapper

    @Autowired
    private lateinit var fleetReferenceMapper: AbstractFleetReferenceMapper

    @Autowired
    private lateinit var notificationRuleEntityRepository: INotificationRuleEntityRepository

    @Autowired
    private lateinit var notificationRuleMapper: INotificationRuleMapper

    override fun getAllVehicleStates(): List<VehicleState> {
        return vehicleStateEntityRepository.findAll().map { vehicleStateEntity ->
            vehicleStateMapper.entityToModel(vehicleStateEntity)
        }
    }

    override fun getVehicleState(vehicleStateId: Long): VehicleState? {
        val vehicleStateEntity = vehicleStateEntityRepository.getOne(vehicleStateId)

        return vehicleStateMapper.entityToModel(vehicleStateEntity)
    }

    override fun createVehicleStates(vehicleStates: List<VehicleState>): List<VehicleState> {
        // Use merge so that the persistence layer does not
        // try to create existing entities this references,
        // but instead uses the already existing ones.
        val vehicleStateEntitiesToSave = vehicleStates.map { vehicleState ->
            entityManager.merge(vehicleStateMapper.modelToEntity(vehicleState))
        }

        vehicleStateEntitiesToSave.map { vehicleStateEntityToSave ->
            vehicleStateEntityRepository.save(vehicleStateEntityToSave)
        }

        return vehicleStateEntitiesToSave.mapNotNull { savedVehicleStateEntity ->
            vehicleStateMapper.entityToModel(savedVehicleStateEntity)
        }
    }

    override fun deleteVehicleState(vehicleStateId: Long) {
        vehicleStateEntityRepository.deleteById(vehicleStateId)
    }

    override fun getAllFleetReferences(): List<FleetReference> {
        return fleetReferenceEntityEntityRepository.findAll().map { fleetReference ->
            fleetReferenceMapper.entityToModel(fleetReference)
        }
    }

    override fun getUnprocessedVehicleStateForRule(notificationRule: NotificationRule): List<VehicleState> {
        val ruleId = notificationRule.ruleId
        val lastUpdate = notificationRule.lastUpdate

        val queryString = """SELECT states FROM VehicleStateEntity states
            |WHERE states.created > :lastUpdate
            |AND NOT EXISTS (SELECT processed FROM NotificationRuleEntity rules JOIN
            | rules.processedVehicleStates processed
            | WHERE rules.ruleId = :ruleId
            | AND processed = states)
        """.trimMargin()

        val query = entityManager.createQuery(queryString, VehicleStateEntity::class.java)
                .setParameter("lastUpdate", lastUpdate)
                .setParameter("ruleId", ruleId)

        val vehicleStateEntities = query.resultList
        return vehicleStateEntities.map { vehicleStateEntity ->
            vehicleStateMapper.entityToModel(vehicleStateEntity)
        }
    }

    override fun markVehicleStateAsProcessedByRule(
        notificationRule: NotificationRule,
        vehicleStates: List<VehicleState>
    ) {
        val notificationRuleEntityToSave = entityManager.merge(
                notificationRuleMapper.modelToEntity(notificationRule)
        )
        val vehicleStateEntities = vehicleStates.map { vehicleState ->
            entityManager.merge(vehicleStateMapper.modelToEntity(vehicleState))
        }.toSet()

        notificationRuleEntityToSave.processedVehicleStates = notificationRuleEntityToSave
                .processedVehicleStates?.plus(vehicleStateEntities) ?: vehicleStateEntities

        notificationRuleEntityRepository.save(notificationRuleEntityToSave)
    }
}
