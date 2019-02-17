package de.unia.se.teamcq.scheduling.service

import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.scheduling.job.ScheduledAggregatorRuleJob
import de.unia.se.teamcq.scheduling.job.VehicleStateDataImportJob
import de.unia.se.teamcq.scheduling.job.VehicleStateDataProcessingJob
import org.quartz.CronExpression
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.text.ParseException

@Component
class NotificationScheduler : INotificationScheduler {

    @Autowired
    private lateinit var scheduler: Scheduler

    @Value("\${de.unia.se.teamcq.scheduling.data-import-cron:0 0/1 * * * ?}")
    private lateinit var dataImportCronString: String

    @Value("\${de.unia.se.teamcq.scheduling.data-processing-cron:0 0/1 * * * ?}")
    private lateinit var dataProcessingCronString: String

    override fun updateNotificationRuleScheduleAsNecessary(notificationRule: NotificationRule?) {
        notificationRule?.let { ruleToUpdateScheduleFor ->
            try {
                val ruleId = ruleToUpdateScheduleFor.ruleId!!
                deleteNotificationRuleSchedule(ruleToUpdateScheduleFor.ruleId!!)

                val aggregator = ruleToUpdateScheduleFor.aggregator
                if (aggregator is AggregatorScheduled) {
                    val notificationCronSchedule = aggregator.notificationCronTrigger

                    val jobDetail = buildScheduledAggregatorRuleJobDetail(ruleId)
                    val trigger = buildJobTrigger(jobDetail, notificationCronSchedule!!)
                    scheduler.scheduleJob(jobDetail, trigger)
                }
            } catch (schedulerException: SchedulerException) {
                logger.error("Error while scheduling the NotificationRule {$notificationRule}", schedulerException)
            }
        }
    }

    override fun deleteNotificationRuleSchedule(ruleId: Long) {
        scheduler.deleteJob(getScheduledNotificationJobKey(ruleId))
    }

    override fun scheduleVehicleStateDataImport() {
        try {
            val jobDetail = buildDataImportJobDetail()
            val dataImportCronExpression = CronExpression(dataImportCronString)
            val trigger = buildJobTrigger(jobDetail, dataImportCronExpression)
            scheduler.scheduleJob(jobDetail, mutableSetOf(trigger), true)
        } catch (schedulerException: SchedulerException) {
            logger.error("Error while scheduling the VehicleState Import", schedulerException)
        } catch (parseException: ParseException) {
            logger.error("Error while parsing the VehicleState Import cron expression", parseException)
        }
    }

    override fun scheduleVehicleStateDataProcessing() {
        try {
            val jobDetail = buildDataProcessingJobDetail()
            val dataProcessingCronExpression = CronExpression(dataProcessingCronString)
            val trigger = buildJobTrigger(jobDetail, dataProcessingCronExpression)
            scheduler.scheduleJob(jobDetail, mutableSetOf(trigger), true)
        } catch (schedulerException: SchedulerException) {
            logger.error("Error while scheduling the VehicleState Processing", schedulerException)
        } catch (parseException: ParseException) {
            logger.error("Error while parsing the VehicleState Processing cron expression", parseException)
        }
    }

    companion object {

        private val logger = LoggerFactory.getLogger(NotificationScheduler::class.java)

        private fun getScheduledNotificationJobKey(ruleId: Long): JobKey {
            return JobKey(ruleId.toString(), "scheduled-rules")
        }

        private fun buildScheduledAggregatorRuleJobDetail(ruleId: Long): JobDetail {
            val jobDataMap = JobDataMap()

<<<<<<< HEAD
            jobDataMap["ruleId"] = ruleId
=======
            jobDataMap["ruleId"] = ruleId.toString()
>>>>>>> master

            return JobBuilder.newJob(ScheduledAggregatorRuleJob::class.java)
                    .withIdentity(getScheduledNotificationJobKey(ruleId))
                    .withDescription("Send Notifications for a Rule with a scheduled Aggregator")
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build()
        }

        private fun buildDataImportJobDetail(): JobDetail {
            return JobBuilder.newJob(VehicleStateDataImportJob::class.java)
                    .withIdentity("data-import", "vehicle-state-jobs")
                    .withDescription("Import new VehicleState data")
                    .storeDurably()
                    .build()
        }

        private fun buildDataProcessingJobDetail(): JobDetail {
            return JobBuilder.newJob(VehicleStateDataProcessingJob::class.java)
                    .withIdentity("data-processing", "vehicle-state-jobs")
                    .withDescription("Process unprocessed imported VehicleState data")
                    .storeDurably()
                    .build()
        }

        private fun buildJobTrigger(jobDetail: JobDetail, cronExpression: CronExpression): Trigger {
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobDetail.key.name, "notification-triggers")
                    .withDescription("Cron Trigger")
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cronExpression)
                            .withMisfireHandlingInstructionFireAndProceed())
                    .build()
        }
    }
}