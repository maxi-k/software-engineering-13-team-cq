package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.ruleevaluation.service.IEvaluationService
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import de.unia.se.teamcq.vehiclestate.service.VehicleStateService
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
class RuleStateProcessingServiceTest : StringSpec() {

    @MockK
    private lateinit var notificationRuleService: INotificationRuleService

    @MockK
    private lateinit var evaluationService: IEvaluationService

    @MockK
    private lateinit var notificationService: INotificationService

    @MockK
    private lateinit var vehicleStateService: VehicleStateService

    @InjectMockKs
    private lateinit var ruleStateProcessingService: RuleStateProcessingService

    init {
        MockKAnnotations.init(this)

        "ProcessNewVehicleStates should be idempotent and trigger notifications accordingly" {

            every { notificationRuleService.getNotificationRule(50) } returns
                    getTestNotificationRuleModel()

            every { vehicleStateService.getUnprocessedVehicleStateForRule(any()) } returns
                    listOf(getTestVehicleStateModel())

            every { evaluationService.checkCondition(any(), any()) } returns true

            every { notificationService.storeNotificationData(any()) } just Runs

            every { vehicleStateService.markVehicleStateAsProcessedByRule(any(), any()) } just Runs

            every { notificationService.sendNotificationForRuleIfNecessary(any()) } just Runs

            ruleStateProcessingService.processNewVehicleStatesForRule(50)

            verify(exactly = 1) {
                notificationService.storeNotificationData(any())
                notificationService.sendNotificationForRuleIfNecessary(any())
            }
        }
    }
}
