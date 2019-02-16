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
class VehicleStateDataProcessingJob : QuartzJobBean() {

    @Autowired
    private lateinit var ruleStateProcessingService: IRuleStateProcessingService

    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        logger.info("Starting to process new Vehicle States")
        val started = System.currentTimeMillis()
        ruleStateProcessingService.processNewVehicleStates()
        val time = (System.currentTimeMillis() - started) / 1000
        logger.info("Finished processing new Vehicle States in {} s", time)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateDataProcessingJob::class.java)
    }
}
