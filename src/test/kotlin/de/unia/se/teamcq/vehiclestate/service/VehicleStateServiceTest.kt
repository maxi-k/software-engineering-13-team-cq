package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.model.IVehicleStateDataType
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateServiceTest : StringSpec() {

    @MockK
    lateinit var mockedVehicleStateDataTypes: Set<IVehicleStateDataType>

    @InjectMockKs
    lateinit var vehicleStateService: VehicleStateService

    init {
        MockKAnnotations.init(this)

        "ImportNewVehicleData" should {
            "Import new VehicleStates correctly" {
                vehicleStateService.importNewVehicleData() // TODO
            }
        }

        "GetVehicleStateDataTypes should return all expected VehicleStateDataTypes" {

            val vehicleStateDataTypes = vehicleStateService.getVehicleStateDataTypes()
            vehicleStateDataTypes shouldBe mockedVehicleStateDataTypes
        }
    }
}
