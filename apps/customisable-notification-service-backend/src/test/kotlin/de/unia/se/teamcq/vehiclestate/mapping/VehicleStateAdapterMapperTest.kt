package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateAdapterMapperTest : StringSpec() {

    @InjectMockKs
    lateinit var vehicleStateMapper: VehicleStateAdapterMapper

    init {
        MockKAnnotations.init(this)

        "Convert VSS Vehicle to VehicleState" {

            val vehicle = TestUtils.getTestVSSVehicle()

            val vehicleStateModel = vehicleStateMapper.dtoToModel(vehicle)

            val vehicleStateModelWithoutIDs = getTestVehicleStateModel().apply {
                stateId = null
                vehicleStateDataTypes.forEach { dataType ->
                    dataType.dataTypeId = null
                }
            }

            // Comparing the container objects would work too, but would make debugging harder when
            // adding new fields
            vehicleStateModel.stateId shouldBe vehicleStateModelWithoutIDs.stateId
            vehicleStateModel.vehicleReference shouldBe vehicleStateModelWithoutIDs.vehicleReference
            vehicleStateModel.vehicleStateDataTypes.size shouldBe
                    vehicleStateModelWithoutIDs.vehicleStateDataTypes.size
            dataTypeShouldBeEqual(vehicleStateModel, vehicleStateModelWithoutIDs, "Battery")
            dataTypeShouldBeEqual(vehicleStateModel, vehicleStateModelWithoutIDs, "Engine")
            dataTypeShouldBeEqual(vehicleStateModel, vehicleStateModelWithoutIDs, "Fuel")
            dataTypeShouldBeEqual(vehicleStateModel, vehicleStateModelWithoutIDs, "Contract")
            dataTypeShouldBeEqual(vehicleStateModel, vehicleStateModelWithoutIDs, "Mileage")
            dataTypeShouldBeEqual(vehicleStateModel, vehicleStateModelWithoutIDs, "Service")
        }
    }

    private fun dataTypeShouldBeEqual(
        vehicleStateModel: VehicleState,
        vehicleStateModelWithoutIDs: VehicleState,
        dataTypeName: String
    ) {
        vehicleStateModel.vehicleStateDataTypes.first { dataType ->
            dataType.predicateFieldProviderName == dataTypeName
        } shouldBe vehicleStateModelWithoutIDs.vehicleStateDataTypes.first { dataType ->
            dataType.predicateFieldProviderName == dataTypeName
        }
    }
}
