package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.notificationmanagement.service.NotificationService
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
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
class ScheduledAggregatorRuleJobTest : StringSpec() {

    @MockK
    private lateinit var notificationService: NotificationService

    @MockK
    private lateinit var notificationRuleService: INotificationRuleService

    @InjectMockKs
    private lateinit var scheduledAggregatorRuleJob: ScheduledAggregatorRuleJob

    init {
        MockKAnnotations.init(this)

        "ExecuteInternal should send Notifications" {

            val jobExecutionContext = mockk<JobExecutionContext>()
            val jobDetail = mockk<JobDetail>()

            val jobDataMap = JobDataMap()
            jobDataMap["ruleId"] = "2"

            val notificationRule = getTestNotificationRuleModel()

            every { jobExecutionContext.jobDetail } returns jobDetail
            every { jobDetail.key } returns JobKey("1", "notification-triggers")
            every { jobExecutionContext.mergedJobDataMap } returns jobDataMap
            every { notificationRuleService.getNotificationRule(any()) } returns notificationRule
            every { notificationService.sendNotificationForScheduledRule(any()) } just Runs

            invokeExecuteInternal(scheduledAggregatorRuleJob, jobExecutionContext)

            verify(exactly = 1) {
                notificationService.sendNotificationForScheduledRule(any())
            }
        }
    }

    // Can't test this without reflection because Kotlin interprets protected differently than Java and
    // ScheduledAggregatorRuleJob overrides a protected Quartz method
    private fun invokeExecuteInternal(
        scheduledAggregatorRuleJob: ScheduledAggregatorRuleJob,
        jobExecutionContext: JobExecutionContext
    ) {

        val method = scheduledAggregatorRuleJob.javaClass.getDeclaredMethod("executeInternal", JobExecutionContext::class.java)
        method.isAccessible = true
        method.invoke(scheduledAggregatorRuleJob, jobExecutionContext)
    }
}
