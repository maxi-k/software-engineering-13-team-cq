package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeMileageEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeMileage
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeMileageMapper {

    fun modelToEntity(vehicleStateDataTypeMileage: VehicleStateDataTypeMileage): VehicleStateDataTypeMileageEntity

    fun entityToModel(vehicleStateDataTypeMileageEntity: VehicleStateDataTypeMileageEntity): VehicleStateDataTypeMileage
}
