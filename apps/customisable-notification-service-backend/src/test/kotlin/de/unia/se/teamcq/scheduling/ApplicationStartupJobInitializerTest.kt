package de.unia.se.teamcq.scheduling

import de.unia.se.teamcq.scheduling.service.INotificationScheduler
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class ApplicationStartupJobInitializerTest : StringSpec() {

    @MockK
    private lateinit var notificationScheduler: INotificationScheduler

    @InjectMockKs
    private lateinit var applicationStartupJobInitializer: ApplicationStartupJobInitializer

    init {
        MockKAnnotations.init(this)

        "InitializeVehicleStateProcessingJobs should initialize Data Import and Processing" should {

            every { notificationScheduler.scheduleVehicleStateDataImport() } just Runs

            applicationStartupJobInitializer.initializeVehicleStateProcessingJobs()

            verify(exactly = 1) {
                notificationScheduler.scheduleVehicleStateDataImport()
            }
        }
    }
}
