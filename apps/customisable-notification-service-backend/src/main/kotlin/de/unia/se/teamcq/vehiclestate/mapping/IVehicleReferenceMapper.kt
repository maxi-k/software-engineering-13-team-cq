package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleReferenceEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleReference
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [AbstractFleetReferenceMapper::class])
interface IVehicleReferenceMapper {

    fun modelToEntity(vehicleReference: VehicleReference): VehicleReferenceEntity

    fun entityToModel(vehicleReferenceEntity: VehicleReferenceEntity): VehicleReference
}
