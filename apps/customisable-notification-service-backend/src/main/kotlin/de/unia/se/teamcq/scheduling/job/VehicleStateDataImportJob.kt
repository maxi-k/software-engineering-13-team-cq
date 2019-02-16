package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.vehiclestate.service.VehicleStateService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class VehicleStateDataImportJob : QuartzJobBean() {

    @Autowired
    private lateinit var vehicleStateService: VehicleStateService

    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        logger.info("Starting to import new Vehicle States")
        val started = System.currentTimeMillis()
        vehicleStateService.importNewVehicleData()
        val time = (System.currentTimeMillis() - started) / 1000.0
        logger.info("Finished importing new Vehicle States in {} s", time)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateDataImportJob::class.java)
    }
}
