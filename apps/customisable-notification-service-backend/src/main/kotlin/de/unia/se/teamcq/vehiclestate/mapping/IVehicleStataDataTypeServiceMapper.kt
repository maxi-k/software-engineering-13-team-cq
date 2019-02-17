package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeServiceEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeService
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IVehicleStateDataTypeServiceMapper {

    fun modelToEntity(vehicleStateDataTypeService: VehicleStateDataTypeService): VehicleStateDataTypeServiceEntity

    fun entityToModel(vehicleStateDataTypeServiceEntity: VehicleStateDataTypeServiceEntity): VehicleStateDataTypeService
}
