package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IVehicleStateDataTypeBatteryMapper {

    fun modelToEntity(vehicleStateDataTypeBattery: VehicleStateDataTypeBattery): VehicleStateDataTypeBatteryEntity

    fun entityToModel(vehicleStateDataTypeBatteryEntity: VehicleStateDataTypeBatteryEntity): VehicleStateDataTypeBattery
}
