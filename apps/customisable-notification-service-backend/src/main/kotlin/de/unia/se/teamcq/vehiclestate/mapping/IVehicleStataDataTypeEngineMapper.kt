package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEngineEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeEngine
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeEngineMapper {

    fun modelToEntity(vehicleStateDataTypeEngine: VehicleStateDataTypeEngine): VehicleStateDataTypeEngineEntity

    fun entityToModel(vehicleStateDataTypeEngineEntity: VehicleStateDataTypeEngineEntity): VehicleStateDataTypeEngine
}
