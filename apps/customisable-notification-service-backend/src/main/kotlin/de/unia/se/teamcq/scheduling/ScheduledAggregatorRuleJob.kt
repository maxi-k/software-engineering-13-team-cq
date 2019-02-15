package de.unia.se.teamcq.scheduling

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
class ScheduledAggregatorRuleJob : QuartzJobBean() {

    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        logger.info("Executing Job with key {}", jobExecutionContext.jobDetail.key)

        val jobDataMap = jobExecutionContext.mergedJobDataMap
        val subject = jobDataMap.getString("subject")
        val body = jobDataMap.getString("body")
        val recipientEmail = jobDataMap.getString("email")

        sendMail("username", recipientEmail, subject, body)
    }

    private fun sendMail(fromEmail: String, toEmail: String, subject: String, body: String) {
        logger.info("Sending Email to {}", toEmail)
        logger.info("fromEmail: {}, subject: {}, body: {}", toEmail)

        // TODO. Code used in Quartz tutorial: https://www.callicoder.com/spring-boot-quartz-scheduler-email-scheduling-example/
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduledAggregatorRuleJob::class.java)
    }
}
