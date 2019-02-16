package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.notificationmanagement.service.IRuleStateProcessingService
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
class VehicleStateDataProcessingJobTest : StringSpec() {

    @MockK
    private lateinit var ruleStateProcessingService: IRuleStateProcessingService

    @InjectMockKs
    private lateinit var vehicleStateDataProcessingJob: VehicleStateDataProcessingJob

    init {
        MockKAnnotations.init(this)

        "ExecuteInternal should send Notifications" {

            val jobExecutionContext = mockk<JobExecutionContext>()

            every { ruleStateProcessingService.processNewVehicleStates() } just Runs

            invokeExecuteInternal(vehicleStateDataProcessingJob, jobExecutionContext)

            verify(exactly = 1) {
                ruleStateProcessingService.processNewVehicleStates()
            }
        }
    }

    // Can't test this without reflection because Kotlin interprets protected differently than Java and
    // VehicleStateDataProcessingJob overrides a protected Spring method
    private fun invokeExecuteInternal(
        vehicleStateDataProcessingJob: VehicleStateDataProcessingJob,
        jobExecutionContext: JobExecutionContext
    ) {

        val method = vehicleStateDataProcessingJob.javaClass.getDeclaredMethod("executeInternal", JobExecutionContext::class.java)
        method.isAccessible = true
        method.invoke(vehicleStateDataProcessingJob, jobExecutionContext)
    }
}
