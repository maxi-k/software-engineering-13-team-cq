package de.unia.se.teamcq.notificationmanagement.service

import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleStateProcessingServiceTest : StringSpec() {

    @InjectMockKs
    private lateinit var ruleStateProcessingService: RuleStateProcessingService

    init {
        MockKAnnotations.init(this)

        "ProcessNewVehicleStates should be idempotent and trigger notifications accordingly" should {
<<<<<<< HEAD
=======
            // TODO: #112 Backend: Set up NotificationRule VehicleStateUpdate Processing
>>>>>>> master
            ruleStateProcessingService.processNewVehicleStates()
        }
    }
}
