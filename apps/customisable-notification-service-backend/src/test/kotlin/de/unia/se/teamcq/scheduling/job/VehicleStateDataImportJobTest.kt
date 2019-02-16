package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.scheduling.job.JobTestUtils.invokeScheduledJobExecuteInternal
import de.unia.se.teamcq.vehiclestate.service.VehicleStateService
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.quartz.JobExecutionContext
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataImportJobTest : StringSpec() {

    @MockK
    private lateinit var vehicleStateService: VehicleStateService

    @InjectMockKs
    private lateinit var vehicleStateDataImportJob: VehicleStateDataImportJob

    init {
        MockKAnnotations.init(this)

        "ExecuteInternal should send Notifications" {

            val jobExecutionContext = mockk<JobExecutionContext>()

            every { vehicleStateService.importNewVehicleData() } just Runs

            invokeScheduledJobExecuteInternal(vehicleStateDataImportJob, jobExecutionContext)

            verify(exactly = 1) {
                vehicleStateService.importNewVehicleData()
            }
        }
    }
}
