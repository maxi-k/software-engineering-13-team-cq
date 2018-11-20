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

    // @Cacheable(value = ["vehicle-status"])
    fun getAllVehicleStatus(): List<VehicleStatus> = vehicleStatusRepository.findAll()

    /*@CacheEvict(value = ["vehicle-status"], allEntries = true)

    @Caching(
            evict = [(CacheEvict(value = ["vehicle-status"], allEntries = true))],
            put = [(CachePut(value = ["vehicle-status-single"], key = "#result.id"))]
    ) */
    fun createNewVehicleStatus(vehicleStatus: VehicleStatus): VehicleStatus =
            vehicleStatusRepository.save(vehicleStatus)

    // @Cacheable(value = ["vehicle-status-single"], key = "#articleId")
    fun getVehicleStatusById(articleId: Long): Optional<VehicleStatus> =
            vehicleStatusRepository.findById(articleId)

    // @CachePut(value = ["vehicle-status-single"], key = "#article.id")
    fun updateArticle(
        vehicleStatus: VehicleStatus,
        newVehicleStatus: VehicleStatus
    ): VehicleStatus {

        val updatedVehicleStatus: VehicleStatus = vehicleStatus
            .copy(kilometers = newVehicleStatus.kilometers, batteryCharge = newVehicleStatus.batteryCharge)
        return vehicleStatusRepository.save(updatedVehicleStatus)
    }

    /*@Caching(
            evict = [
                (CacheEvict(value = ["vehicle-status"], allEntries = true)),
                (CacheEvict(value = ["vehicle-status-single"], key = "#vehicle-status.id"))]
    )*/
    fun deleteVehicleStatus(vehicleStatus: VehicleStatus) =
            vehicleStatusRepository.delete(vehicleStatus)
}