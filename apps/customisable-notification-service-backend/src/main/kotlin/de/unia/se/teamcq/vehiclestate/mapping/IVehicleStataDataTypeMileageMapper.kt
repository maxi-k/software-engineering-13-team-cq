package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeMileageEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeMileage
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IVehicleStateDataTypeMileageMapper {


    fun modelToEntity(vehicleStateDataTypeMileage: VehicleStateDataTypeMileage): VehicleStateDataTypeMileageEntity

    fun entityToModel(vehicleStateDataTypeMileageEntity: VehicleStateDataTypeMileageEntity): VehicleStateDataTypeMileage
}
