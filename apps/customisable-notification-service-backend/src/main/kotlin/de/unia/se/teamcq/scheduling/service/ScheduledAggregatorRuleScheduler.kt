package de.unia.se.teamcq.scheduling.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.scheduling.job.ScheduledAggregatorRuleJob
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.Date
import java.util.UUID

@Component
class ScheduledAggregatorRuleScheduler : IScheduledAggregatorRuleScheduler {

    @Autowired
    private val scheduler: Scheduler? = null

    override fun scheduleNotificationRule(notificationRule: NotificationRule) {
        try {
            val dateTime = ZonedDateTime.now()

            val jobDetail = buildJobDetail("email", "subject", "body")
            val trigger = buildJobTrigger(jobDetail, dateTime)
            scheduler!!.scheduleJob(jobDetail, trigger)
        } catch (ex: SchedulerException) {
            logger.error("Error scheduling email", ex)
        }
    }

    private fun buildJobDetail(email: String, subject: String, body: String): JobDetail {
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

    private fun buildJobTrigger(jobDetail: JobDetail, startAt: ZonedDateTime): Trigger {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.key.name, "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduledAggregatorRuleScheduler::class.java)
    }
}
