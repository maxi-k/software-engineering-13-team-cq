package de.unia.se.teamcq.scheduling.job

import de.unia.se.teamcq.vehiclestate.service.IVehicleStateService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class VehicleStateDataImportJob : QuartzJobBean() {

    @Autowired
    private lateinit var vehicleStateService: IVehicleStateService

    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        vehicleStateService.importNewVehicleData()
    }
}
