package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeContractEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeContract
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeContractMapper {

    fun modelToEntity(vehicleStateDataTypeContract: VehicleStateDataTypeContract): VehicleStateDataTypeContractEntity

    fun entityToModel(vehicleStateDataTypeContractEntity: VehicleStateDataTypeContractEntity): VehicleStateDataTypeContract
}
