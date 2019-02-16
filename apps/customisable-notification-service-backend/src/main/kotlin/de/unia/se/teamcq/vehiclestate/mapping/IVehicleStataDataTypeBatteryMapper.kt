package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeBatteryMapper {

    fun modelToEntity(vehicleStateDataTypeBattery: VehicleStateDataTypeBattery): VehicleStateDataTypeBatteryEntity

    fun entityToModel(vehicleStateDataTypeBatteryEntity: VehicleStateDataTypeBatteryEntity): VehicleStateDataTypeBattery
}
