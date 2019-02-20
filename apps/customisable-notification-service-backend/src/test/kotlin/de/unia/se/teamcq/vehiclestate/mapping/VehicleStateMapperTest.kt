package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleReferenceEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleReferenceModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEntities
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeModels
import de.unia.se.teamcq.TestUtils.getTestVehicleStateEnity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.rulemanagement.mapping.RecipientMapperHelper
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateMapperTest : StringSpec() {

    @MockK
    lateinit var mockIVehicleReferenceMapper: IVehicleReferenceMapper

    @MockK
    lateinit var mockVehicleStateMapperHelper: VehicleStateMapperHelper

    @InjectMockKs
    lateinit var vehicleStateMapper: IVehicleStateMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            every { mockIVehicleReferenceMapper.modelToEntity(any()) } returns getTestVehicleReferenceEntity()

            every { mockVehicleStateMapperHelper.modelToEntity(any()) } returns getTestVehicleStateDataTypeEntities()

            val vehicleState = getTestVehicleStateModel()

            val vehicleStateEntity = vehicleStateMapper.modelToEntity(vehicleState)

            vehicleStateEntity shouldBe getTestVehicleStateEnity()
        }

        "Convert entity to model" {

            every { mockIVehicleReferenceMapper.entityToModel(any()) } returns getTestVehicleReferenceModel()

            every { mockVehicleStateMapperHelper.entityToModel(any()) } returns getTestVehicleStateDataTypeModels()

            val vehicleStateEntity = getTestVehicleStateEnity()

            val vehicleStateModel = vehicleStateMapper.entityToModel(vehicleStateEntity)

            vehicleStateModel shouldBe getTestVehicleStateModel()

        }
    }
}
