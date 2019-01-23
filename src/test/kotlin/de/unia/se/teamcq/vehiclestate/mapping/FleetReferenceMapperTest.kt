package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceEntity
import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class FleetReferenceMapperTest : StringSpec() {

    private var fleetReferenceMapper: IFleetReferenceMapper = Mappers.getMapper(IFleetReferenceMapper::class.java)

    init {

        "Convert model to entity" {

            val fleetReferenceModel = getTestFleetReferenceModel()

            val fleetReferenceEntity = fleetReferenceMapper.modelToEntity(fleetReferenceModel)

            fleetReferenceEntity shouldBe getTestFleetReferenceEntity()
        }

        "Convert entity to model" {

            val fleetReferenceEntity = getTestFleetReferenceEntity()

            val fleetReferenceModel = fleetReferenceMapper.entityToModel(fleetReferenceEntity)

            fleetReferenceModel shouldBe getTestFleetReferenceModel()
        }
    }
}
