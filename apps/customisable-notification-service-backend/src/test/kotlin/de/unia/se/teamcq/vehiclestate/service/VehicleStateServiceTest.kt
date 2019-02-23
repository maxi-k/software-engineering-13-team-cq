package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import io.kotlintest.should
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateServiceTest : StringSpec() {

    @MockK
    lateinit var vehicleStateRepository: IVehicleStateRepository

    @InjectMockKs
    lateinit var vehicleStateService: VehicleStateService

    init {
        MockKAnnotations.init(this)

        "ImportNewVehicleData" should {
            "Import new VehicleStates correctly" {

                ReflectionTestUtils.setField(vehicleStateService, "authenticationUsername", "admin")
                ReflectionTestUtils.setField(vehicleStateService, "authenticationPassword", "fd123!")

                vehicleStateService.importNewVehicleData()
            }
        }
    }
}
