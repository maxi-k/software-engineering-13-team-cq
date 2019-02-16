package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeFuelEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeFuel
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IVehicleStateDataTypeFuelMapper {

    fun modelToEntity(vehicleStateDataTypeFuel: VehicleStateDataTypeFuel): VehicleStateDataTypeFuelEntity

    fun entityToModel(vehicleStateDataTypeFuelEntity: VehicleStateDataTypeFuelEntity): VehicleStateDataTypeFuel
}
