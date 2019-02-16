package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Service


@Service
interface IVehicleStateDataTypeMapper {

    fun modelToEntity(vehicleStateDataType: VehicleStateDataType): VehicleStateDataTypeEntity

    fun entityToModel(vehicleStateDataTypeEntity: VehicleStateDataTypeEntity): VehicleStateDataType
}
