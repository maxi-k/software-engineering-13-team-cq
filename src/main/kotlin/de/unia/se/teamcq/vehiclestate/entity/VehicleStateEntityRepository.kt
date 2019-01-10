package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateMapper
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface IVehicleStateEntityRepository : JpaRepository<VehicleStateEntity, Long>

@Component
class VehicleStateRepository : IVehicleStateRepository {

    @Autowired
    lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    @Autowired
    lateinit var vehicleStateMapper: IVehicleStateMapper

    override fun getAllVehicleStates(): List<VehicleState> {
        return vehicleStateEntityRepository.findAll().map { vehicleStateEntity ->
            vehicleStateMapper.entityToModel(vehicleStateEntity)
        }
    }

    override fun getVehicleState(vehicleStateId: Long): VehicleState? {
        val vehicleStateEntity = vehicleStateEntityRepository.findById(vehicleStateId).get()

        return vehicleStateMapper.entityToModel(vehicleStateEntity)
    }

    override fun createVehicleState(vehicleState: VehicleState): VehicleState? {
        val vehicleStateEntityToSave = vehicleStateMapper.modelToEntity(vehicleState)

        val savedVehicleStateEntity = vehicleStateEntityRepository.save(vehicleStateEntityToSave)

        return vehicleStateMapper.entityToModel(savedVehicleStateEntity)
    }

    override fun updateVehicleState(vehicleState: VehicleState): VehicleState? {
        val vehicleStateEntityToSave = vehicleStateMapper.modelToEntity(vehicleState)

        val savedVehicleStateEntity = vehicleStateEntityRepository.save(vehicleStateEntityToSave)

        return vehicleStateMapper.entityToModel(savedVehicleStateEntity)
    }

    override fun deleteVehicleState(vehicleStateId: Long) {
        vehicleStateEntityRepository.deleteById(vehicleStateId)
    }
}
