package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.dto.FleetReferenceDto
import de.unia.se.teamcq.vehiclestate.entity.FleetReferenceEntity
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class AbstractFleetReferenceMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(fleetReferenceDto: FleetReferenceDto) {

        if (fleetReferenceDto.carParkId.isNullOrBlank()) {
            throw IllegalArgumentException("Attribute carParkId of FleetReferenceDto" +
                    " is required but was null or blank!")
        }

        if (fleetReferenceDto.fleetId.isNullOrBlank()) {
            throw IllegalArgumentException("Attribute fleetId of FleetReferenceDto" +
                    " is required but was null or blank!")
        }
    }

    abstract fun modelToEntity(fleetReference: FleetReference): FleetReferenceEntity

    abstract fun entityToModel(fleetReferenceEntity: FleetReferenceEntity): FleetReference

    abstract fun modelToDto(fleetReference: FleetReference): FleetReferenceDto

    abstract fun dtoToModel(fleetReferenceDto: FleetReferenceDto): FleetReference
}
