package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceDto
import de.unia.se.teamcq.TestUtils.getTestFleetReferenceEntity
import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class FleetReferenceMapperTest : StringSpec() {

    private var fleetReferenceMapper: AbstractFleetReferenceMapper = Mappers.getMapper(AbstractFleetReferenceMapper::class.java)

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

        "Convert model to dto" {

            val fleetReferenceModel = getTestFleetReferenceModel()

            val fleetReferenceDto = fleetReferenceMapper.modelToDto(fleetReferenceModel)

            fleetReferenceDto shouldBe getTestFleetReferenceDto()
        }

        "Convert dto to model" should {

            "Work with legal arguments" {

                val fleetReferenceDto = getTestFleetReferenceDto()

                val fleetReferenceModel = fleetReferenceMapper.dtoToModel(fleetReferenceDto)

                fleetReferenceModel shouldBe getTestFleetReferenceModel()
            }

            "Throw an Exception if the fleetId is null or blank" {

                shouldThrow<IllegalArgumentException> {

                    val fleetReferenceDto = getTestFleetReferenceDto().apply {
                        fleetId = null
                    }

                    fleetReferenceMapper.dtoToModel(fleetReferenceDto)
                }

                shouldThrow<IllegalArgumentException> {

                    val fleetReferenceDto = getTestFleetReferenceDto().apply {
                        fleetId = ""
                    }

                    fleetReferenceMapper.dtoToModel(fleetReferenceDto)
                }
            }

            "Throw an Exception if the carParkId is null or blank" {

                shouldThrow<IllegalArgumentException> {

                    val fleetReferenceDto = getTestFleetReferenceDto().apply {
                        carParkId = null
                    }

                    fleetReferenceMapper.dtoToModel(fleetReferenceDto)
                }

                shouldThrow<IllegalArgumentException> {

                    val fleetReferenceDto = getTestFleetReferenceDto().apply {
                        carParkId = ""
                    }

                    fleetReferenceMapper.dtoToModel(fleetReferenceDto)
                }
            }
        }
    }
}
