package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [IVehicleReferenceMapper::class])
interface IVehicleStateMapper {

    fun modelToEntity(vehicleState: VehicleState): VehicleStateEntity

    fun entityToModel(vehicleStateEntity: VehicleStateEntity): VehicleState
}
