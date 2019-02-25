package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.notificationmanagement.service.IRuleStateProcessingService
import de.unia.se.teamcq.scheduling.job.JobTestUtils.invokeScheduledJobExecuteInternal
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.quartz.JobKey
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationRuleJobTest : StringSpec() {

    @MockK
    private lateinit var ruleStateProcessingService: IRuleStateProcessingService

    @InjectMockKs
    private lateinit var notificationRuleJob: NotificationRuleJob

    init {
        MockKAnnotations.init(this)

        "ExecuteInternal should send Notifications" {

            val jobExecutionContext = mockk<JobExecutionContext>()
            val jobDetail = mockk<JobDetail>()

            val jobDataMap = JobDataMap()
            jobDataMap["ruleId"] = "2"

            every { jobExecutionContext.jobDetail } returns jobDetail
            every { jobDetail.key } returns JobKey("1", "notification-triggers")
            every { jobExecutionContext.mergedJobDataMap } returns jobDataMap
            every { ruleStateProcessingService.processNewVehicleStatesForRule(any()) } just Runs

            invokeScheduledJobExecuteInternal(notificationRuleJob, jobExecutionContext)

            verify(exactly = 1) {
                ruleStateProcessingService.processNewVehicleStatesForRule(any())
            }
        }
    }
}
