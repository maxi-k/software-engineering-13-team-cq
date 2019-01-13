package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateServiceTest : StringSpec() {

    @MockK
    private lateinit var vehicleStateRepository: IVehicleStateRepository

    @InjectMockKs
    private lateinit var vehicleStateService: VehicleStateService

    init {
        MockKAnnotations.init(this)

        "ImportNewVehicleData" should {
            "work" {
                vehicleStateService.importNewVehicleData()
            }
        }
    }
}
