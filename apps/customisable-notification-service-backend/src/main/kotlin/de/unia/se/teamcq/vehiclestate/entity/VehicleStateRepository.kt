package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateMapper
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

@Component
@Transactional
class VehicleStateRepository : IVehicleStateRepository {

    @Autowired
    private lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    @Autowired
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var vehicleStateMapper: IVehicleStateMapper

    override fun getAllVehicleStates(): List<VehicleState> {
        return vehicleStateEntityRepository.findAll().map { vehicleStateEntity ->
            vehicleStateMapper.entityToModel(vehicleStateEntity)
        }
    }

    override fun getVehicleState(vehicleStateId: Long): VehicleState? {
        val vehicleStateEntity = vehicleStateEntityRepository.getOne(vehicleStateId)

        return vehicleStateMapper.entityToModel(vehicleStateEntity)
    }

    override fun createVehicleState(vehicleState: VehicleState): VehicleState? {
        // Use merge so that the persistence layer does not
        // try to create existing entities this references,
        // but instead uses the already existing ones.
        val vehicleStateEntityToSave = entityManager.merge(
                vehicleStateMapper.modelToEntity(vehicleState)
        )

        val savedVehicleStateEntity = vehicleStateEntityRepository.save(vehicleStateEntityToSave)

        return vehicleStateMapper.entityToModel(savedVehicleStateEntity)
    }

    override fun updateVehicleState(vehicleState: VehicleState): VehicleState? {
        // Use merge so that the persistence layer does not
        // try to create existing entities this references,
        // but instead uses the already existing ones.
        val vehicleStateEntityToSave = entityManager.merge(
                vehicleStateMapper.modelToEntity(vehicleState)
        )

        val savedVehicleStateEntity = vehicleStateEntityRepository.save(vehicleStateEntityToSave)

        return vehicleStateMapper.entityToModel(savedVehicleStateEntity)
    }

    override fun deleteVehicleState(vehicleStateId: Long) {
        vehicleStateEntityRepository.deleteById(vehicleStateId)
    }
}
