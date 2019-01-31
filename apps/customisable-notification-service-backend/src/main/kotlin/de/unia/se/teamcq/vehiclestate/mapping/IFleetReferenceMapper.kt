package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.dto.FleetReferenceDto
import de.unia.se.teamcq.vehiclestate.entity.FleetReferenceEntity
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IFleetReferenceMapper {

    fun modelToEntity(fleetReference: FleetReference): FleetReferenceEntity

    fun entityToModel(fleetReferenceEntity: FleetReferenceEntity): FleetReference

    fun modelToDto(fleetReference: FleetReference): FleetReferenceDto

    fun dtoToModel(fleetReferenceDto: FleetReferenceDto): FleetReference
}
