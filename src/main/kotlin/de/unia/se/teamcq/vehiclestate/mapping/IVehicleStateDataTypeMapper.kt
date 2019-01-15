package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.dto.VehicleStateDataTypeDto
import de.unia.se.teamcq.vehiclestate.model.IVehicleStateDataType
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeMapper {

    fun modelToDto(vehicleStateDataType: IVehicleStateDataType): VehicleStateDataTypeDto
}
