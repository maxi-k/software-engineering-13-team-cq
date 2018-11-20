package de.unia.se.teamcq.events.controller

import de.unia.se.teamcq.events.model.VehicleStatus
import de.unia.se.teamcq.events.service.VehicleStatusService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/events")
class ArticleController(private val vehicleStatusService: VehicleStatusService) {

    @GetMapping("/vehicle-status")
    fun getAllVehicleStatus(): List<VehicleStatus> =
            vehicleStatusService.getAllVehicleStatus()

    @PostMapping("/vehicle-status")
    fun createNewVehicleStatus(@Valid @RequestBody vehicleStatus: VehicleStatus): VehicleStatus =
            vehicleStatusService.createNewVehicleStatus(vehicleStatus)

    @GetMapping("/vehicle-status/{id}")
    fun getVehicleStatusById(@PathVariable(value = "id") vehicleStatusId: Long): ResponseEntity<VehicleStatus> {
        return vehicleStatusService.getVehicleStatusById(vehicleStatusId).map { vehicleStatus ->
            ResponseEntity.ok(vehicleStatus)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/vehicle-status/{id}")
    fun updateVehicleStatusById(@PathVariable(value = "id") vehicleStatusId: Long,
                                @Valid @RequestBody newArticle: VehicleStatus): ResponseEntity<VehicleStatus> {
        return vehicleStatusService.getVehicleStatusById(vehicleStatusId).map { vehicleStatus ->
            ResponseEntity.ok().body(vehicleStatusService.updateArticle(vehicleStatus, newArticle))
        }.orElse(ResponseEntity.notFound().build())
    }


    @DeleteMapping("/vehicle-status/{id}")
    fun deleteVehicleStatusById(@PathVariable(value = "id") vehicleStatusId: Long): ResponseEntity<Void> {
        return vehicleStatusService.getVehicleStatusById(vehicleStatusId).map { vehicleStatus ->
            vehicleStatusService.deleteVehicleStatus(vehicleStatus)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}