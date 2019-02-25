package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.notificationmanagement.service.IRuleStateProcessingService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class NotificationRuleJob : QuartzJobBean() {

    @Autowired
    private lateinit var ruleStateProcessingService: IRuleStateProcessingService

    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        logger.info("Sending notifications for scheduled notification rule job {}", jobExecutionContext.jobDetail.key)

        val jobDataMap = jobExecutionContext.mergedJobDataMap
        val ruleId = jobDataMap.getLongValueFromString("ruleId")

        ruleStateProcessingService.processNewVehicleStatesForRule(ruleId)

        logger.info("Finished processing notification rule job {}",
                jobExecutionContext.jobDetail.key)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationRuleJob::class.java)
    }
}
