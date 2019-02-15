package de.unia.se.teamcq.scheduling

import de.unia.se.teamcq.scheduling.service.NotificationScheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupJobInitializer {

    @Autowired
    private lateinit var notificationScheduler: NotificationScheduler

    @EventListener(ApplicationReadyEvent::class)
    fun doSomethingAfterStartup() {
        notificationScheduler.scheduleVehicleStateDataImport()
        notificationScheduler.scheduleVehicleStateDataProcessing()
    }
}
