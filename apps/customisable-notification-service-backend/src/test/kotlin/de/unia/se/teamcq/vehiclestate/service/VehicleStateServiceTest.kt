package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateServiceTest : StringSpec() {

    @MockK
    private lateinit var vssAdapter: IVssAdapter

    @MockK
    private lateinit var vehicleStateRepository: IVehicleStateRepository

    @InjectMockKs
    private lateinit var vehicleStateService: VehicleStateService

    init {
        MockKAnnotations.init(this)

        "ImportNewVehicleData should fetch and persist new VehicleStates" {

            every { vehicleStateRepository.getAllFleetReferences() } returns listOf(getTestFleetReferenceModel())
            every { vssAdapter.getNewVehicleStates(any()) } returns listOf(getTestVehicleStateModel())
            every { vehicleStateRepository.createVehicleState(any()) } returns getTestVehicleStateModel()

            vehicleStateService.importNewVehicleData()

            verify(exactly = 1) {
                vehicleStateRepository.createVehicleState(any())
            }
        }

        "GetUnprocessedVehicleStateForRule should get VehicleStates" {

            every { vehicleStateRepository.getUnprocessedVehicleStateForRule(any()) } returns
                    listOf(getTestVehicleStateModel())

            val notificationRule = getTestNotificationRuleModel()
            vehicleStateService.getUnprocessedVehicleStateForRule(notificationRule)

            verify(exactly = 1) {
                vehicleStateRepository.getUnprocessedVehicleStateForRule(any())
            }
        }

        "MarkVehicleStateAsProcessedByRule should mark VehicleStates" {

            every { vehicleStateRepository.markVehicleStateAsProcessedByRule(any(), any()) } just Runs

            val notificationRule = getTestNotificationRuleModel()
            val vehicleStates = listOf(getTestVehicleStateModel())
            vehicleStateService.markVehicleStateAsProcessedByRule(notificationRule, vehicleStates)

            verify(exactly = 1) {
                vehicleStateRepository.markVehicleStateAsProcessedByRule(any(), any())
            }
        }
    }
}
