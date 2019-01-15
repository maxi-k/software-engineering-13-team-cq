package de.unia.se.teamcq.vehiclestate.controller

import de.unia.se.teamcq.user.service.IUserService
import de.unia.se.teamcq.vehiclestate.dto.VehicleStateDataTypeDto
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateDataTypeMapper
import de.unia.se.teamcq.vehiclestate.service.IVehicleStateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/vehicle-state-data-types")
class VehicleStateDataTypeController {

    @Autowired
    lateinit var vehicleStateService: IVehicleStateService

    @Autowired
    lateinit var vehicleStateDataTypeMapper: IVehicleStateDataTypeMapper

    @Autowired
    lateinit var userService: IUserService

    @GetMapping
    fun getVehicleStateDataTypes(): List<VehicleStateDataTypeDto> {

        val vehicleStateDataTypes = vehicleStateService.getVehicleStateDataTypes()

        return vehicleStateDataTypes
                .map { vehicleStateDataType -> vehicleStateDataTypeMapper.modelToDto(vehicleStateDataType) }
    }
}
