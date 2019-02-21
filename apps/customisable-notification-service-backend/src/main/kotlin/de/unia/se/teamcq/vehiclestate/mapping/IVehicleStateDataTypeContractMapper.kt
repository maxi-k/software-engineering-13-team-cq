package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeContractEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeContract
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IVehicleStateDataTypeContractMapper {

    fun modelToEntity(vehicleStateDataTypeContract: VehicleStateDataTypeContract): VehicleStateDataTypeContractEntity

    fun entityToModel(vehicleStateDataTypeContractEntity: VehicleStateDataTypeContractEntity): VehicleStateDataTypeContract
}
