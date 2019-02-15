package de.unia.se.teamcq.scheduling.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.scheduling.job.ScheduledAggregatorRuleJob
import de.unia.se.teamcq.scheduling.job.VehicleStateDataImportJob
import de.unia.se.teamcq.scheduling.job.VehicleStateDataProcessingJob
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NotificationScheduler : INotificationScheduler {

    @Autowired
    private lateinit var scheduler: Scheduler

    @Value("\${de.unia.se.teamcq.scheduling.data-import-cron:0 0/1 * * * ?}")
    private lateinit var dataImportCronString: String

    @Value("\${de.unia.se.teamcq.scheduling.data-processing-cron:0 0/1 * * * ?}")
    private lateinit var dataProcessingCronString: String

    override fun scheduleNotificationRule(notificationRule: NotificationRule) {
        try {
            val jobDetail = buildScheduledAggregatorRuleJobDetail("email", "subject", "body")
            val trigger = buildJobTrigger(jobDetail, "")
            scheduler.scheduleJob(jobDetail, trigger)
        } catch (ex: SchedulerException) {
            logger.error("Error scheduling email", ex)
        }
    }

    override fun scheduleVehicleStateDataImport() {
        try {
            val jobDetail = buildDataImportJobDetail("email", "subject", "body")
            val trigger = buildJobTrigger(jobDetail, dataImportCronString)
            scheduler.scheduleJob(jobDetail, trigger)
        } catch (ex: SchedulerException) {
            logger.error("Error scheduling email", ex)
        }
    }

    override fun scheduleVehicleStateDataProcessing() {
        try {
            val jobDetail = buildDataProcessingJobDetail("email", "subject", "body")
            val trigger = buildJobTrigger(jobDetail, dataProcessingCronString)
            scheduler.scheduleJob(jobDetail, trigger)
        } catch (ex: SchedulerException) {
            logger.error("Error scheduling email", ex)
        }
    }

    companion object {

        private val logger = LoggerFactory.getLogger(NotificationScheduler::class.java)

        private fun buildScheduledAggregatorRuleJobDetail(email: String, subject: String, body: String): JobDetail {
            val jobDataMap = JobDataMap()

            jobDataMap["email"] = email
            jobDataMap["subject"] = subject
            jobDataMap["body"] = body

            return JobBuilder.newJob(ScheduledAggregatorRuleJob::class.java)
                    .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                    .withDescription("Send Email Job")
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build()
        }

        private fun buildDataImportJobDetail(email: String, subject: String, body: String): JobDetail {
            val jobDataMap = JobDataMap()

            jobDataMap["email"] = email
            jobDataMap["subject"] = subject
            jobDataMap["body"] = body

            return JobBuilder.newJob(VehicleStateDataImportJob::class.java)
                    .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                    .withDescription("Send Email Job")
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build()
        }

        private fun buildDataProcessingJobDetail(email: String, subject: String, body: String): JobDetail {
            val jobDataMap = JobDataMap()

            jobDataMap["email"] = email
            jobDataMap["subject"] = subject
            jobDataMap["body"] = body

            return JobBuilder.newJob(VehicleStateDataProcessingJob::class.java)
                    .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                    .withDescription("Send Email Job")
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build()
        }

        private fun buildJobTrigger(jobDetail: JobDetail, cronExpressionString: String): Trigger {
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobDetail.key.name, "email-triggers")
                    .withDescription("Send Email Trigger")
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cronExpressionString)
                            .withMisfireHandlingInstructionFireAndProceed())
                    .build()
        }
    }
}
