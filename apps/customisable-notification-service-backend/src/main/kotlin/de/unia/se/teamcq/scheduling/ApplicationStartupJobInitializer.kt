package de.unia.se.teamcq.scheduling

import de.unia.se.teamcq.scheduling.service.INotificationScheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupJobInitializer {

    @Autowired
    private lateinit var notificationScheduler: INotificationScheduler

    @EventListener(ApplicationReadyEvent::class)
    fun initializeVehicleStateProcessingJobs() {
        notificationScheduler.scheduleVehicleStateDataImport()
    }
}
