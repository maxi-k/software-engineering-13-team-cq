package de.unia.se.teamcq.events.service

import de.unia.se.teamcq.events.model.VehicleStatus
import de.unia.se.teamcq.events.repository.VehicleStatusRepository
/* import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching */
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class VehicleStatusService(val vehicleStatusRepository: VehicleStatusRepository) {

    fun getAllVehicleStatus(): List<VehicleStatus> = vehicleStatusRepository.findAll()

    fun createNewVehicleStatus(vehicleStatus: VehicleStatus): VehicleStatus =
            vehicleStatusRepository.save(vehicleStatus)

    fun getVehicleStatusById(vehicleStatusId: Long): Optional<VehicleStatus> =
            vehicleStatusRepository.findById(vehicleStatusId)

    fun updateArticle(
        vehicleStatus: VehicleStatus,
        newVehicleStatus: VehicleStatus
    ): VehicleStatus {

        val updatedVehicleStatus: VehicleStatus =
                vehicleStatus.copy(
                        vehicleId = newVehicleStatus.vehicleId,
                        kilometers = newVehicleStatus.kilometers,
                        batteryCharge = newVehicleStatus.batteryCharge)
        return vehicleStatusRepository.save(updatedVehicleStatus)
    }

    fun deleteVehicleStatus(vehicleStatus: VehicleStatus) =
            vehicleStatusRepository.delete(vehicleStatus)
}
