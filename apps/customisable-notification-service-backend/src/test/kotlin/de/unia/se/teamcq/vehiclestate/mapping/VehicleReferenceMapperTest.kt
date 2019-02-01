package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceEntity
import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import de.unia.se.teamcq.TestUtils.getTestVehicleReferenceEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleReferenceModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleReferenceMapperTest : StringSpec() {

    @MockK
    lateinit var mockIFleetReferenceMapper: IFleetReferenceMapper

    @InjectMockKs
    lateinit var vehicleReferenceMapper: IVehicleReferenceMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            every { mockIFleetReferenceMapper.modelToEntity(any()) } returns getTestFleetReferenceEntity()

            val vehicleReferenceModel = getTestVehicleReferenceModel()

            val vehicleReferenceEntity = vehicleReferenceMapper.modelToEntity(vehicleReferenceModel)

            vehicleReferenceEntity shouldBe getTestVehicleReferenceEntity()
        }

        "Convert entity to model" {

            every { mockIFleetReferenceMapper.entityToModel(any()) } returns getTestFleetReferenceModel()

            val fleetReferenceEntity = getTestVehicleReferenceEntity()

            val fleetReferenceModel = vehicleReferenceMapper.entityToModel(fleetReferenceEntity)

            fleetReferenceModel shouldBe getTestVehicleReferenceModel()
        }
    }
}
