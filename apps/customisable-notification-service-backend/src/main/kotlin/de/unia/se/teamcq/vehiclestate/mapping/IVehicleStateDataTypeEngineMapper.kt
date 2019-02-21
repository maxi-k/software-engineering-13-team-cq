package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEngineEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeEngine
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IVehicleStateDataTypeEngineMapper {

    fun modelToEntity(vehicleStateDataTypeEngine: VehicleStateDataTypeEngine): VehicleStateDataTypeEngineEntity

    fun entityToModel(vehicleStateDataTypeEngineEntity: VehicleStateDataTypeEngineEntity): VehicleStateDataTypeEngine
}
