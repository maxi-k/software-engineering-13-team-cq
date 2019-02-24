package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.notificationmanagement.service.INotificationService
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class ScheduledAggregatorRuleJob : QuartzJobBean() {

    @Autowired
    private lateinit var notificationService: INotificationService

    @Autowired
    private lateinit var notificationRuleService: INotificationRuleService

    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        logger.info("Sending notifications for scheduled notification rule job {}", jobExecutionContext.jobDetail.key)

        val jobDataMap = jobExecutionContext.mergedJobDataMap
        val ruleId = jobDataMap.getLongValueFromString("ruleId")

        val notificationRule = notificationRuleService.getNotificationRule(ruleId)
        notificationService.sendNotificationForRuleIfNecessary(notificationRule!!)

        logger.info("Finished sending notifications for scheduled notification rule job {}",
                jobExecutionContext.jobDetail.key)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduledAggregatorRuleJob::class.java)
    }
}
