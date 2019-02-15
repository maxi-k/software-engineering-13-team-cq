package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.dto.VehicleStateDataTypeBatteryDto
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeBatteryMapper {

    fun modelToEntity(vehicleStateDataTypeBattery: VehicleStateDataTypeBattery): VehicleStateDataTypeBatteryEntity

    fun entityToModel(vehicleStateDataTypeBatteryEntity: VehicleStateDataTypeBatteryEntity): VehicleStateDataTypeBattery

    fun modelToDto(vehicleStateDataTypeBattery: VehicleStateDataTypeBattery): VehicleStateDataTypeBatteryDto

    fun dtoToModel(vehicleStateDataTypeBatteryDto: VehicleStateDataTypeBatteryDto): VehicleStateDataTypeBattery
}
