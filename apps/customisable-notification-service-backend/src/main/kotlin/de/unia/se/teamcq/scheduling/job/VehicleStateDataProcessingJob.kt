package de.unia.se.teamcq.scheduling.job

import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class VehicleStateDataProcessingJob : QuartzJobBean() {

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
        logger.info("Data Processing triggered!")
        logger.info("fromEmail: {}, toEmail: {}, subject: {}, body: {}", fromEmail, toEmail, subject, body)
        // TODO. Code used in Quartz tutorial: https://www.callicoder.com/spring-boot-quartz-scheduler-email-scheduling-example/
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateDataProcessingJob::class.java)
    }
}
