package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEntities
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeModels
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateMapperHelperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeMapper: IVehicleStateDataTypeMapper

    @InjectMockKs
    lateinit var vehicleStateMapperHelper: VehicleStateMapperHelper

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            val vehicleStateDataTypes = getTestVehicleStateDataTypeModels()

            vehicleStateMapperHelper.modelToEntity(vehicleStateDataTypes).size shouldBe 6

            verify(exactly = 6) {
                vehicleStateDataTypeMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            val vehicleStateDataTypeEntities = getTestVehicleStateDataTypeEntities()

            vehicleStateMapperHelper.entityToModel(vehicleStateDataTypeEntities).size shouldBe 6

            verify(exactly = 6) {
                vehicleStateDataTypeMapper.entityToModel(any())
            }
        }
    }
}
