package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.core.io.ByteArrayResource
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationServiceTest : StringSpec() {

    @MockK
    private lateinit var notificationTextService: INotificationTextService

    @MockK
    private lateinit var notificationAttachmentService: INotificationAttachmentService

    @MockK(relaxed = true)
    private lateinit var notificationMESAdapter: INotificationMESAdapter

    @MockK
    private lateinit var notificationRuleRepository: INotificationRuleRepository

    @InjectMockKs
    private lateinit var notificationService: NotificationService

    init {
        MockKAnnotations.init(this)

        "SendNotificationForScheduledRule should work" {

            every { notificationTextService.getHtmlMailTextForNotification(any()) } returns ""

            val byteArrayResource = ByteArrayResource(ByteArray(0))
            every { notificationAttachmentService.getCsvAttachment(any()) } returns byteArrayResource

            val vehicleStateMatches = setOf(getTestVehicleStateModel())
            every { notificationRuleRepository.getVehicleStateMatchesForRule(any()) } returns
                    vehicleStateMatches

            every { notificationRuleRepository.deleteAllVehicleStateMatchesForRule(any()) } just Runs

            val notificationRule = getTestNotificationRuleModel()
            notificationService.sendNotificationForRuleIfNecessary(notificationRule)

            verify(exactly = 1) {
                notificationRuleRepository.deleteAllVehicleStateMatchesForRule(any())
            }
        }

        "StoreNotificationData should work" {
            clearMocks(notificationRuleRepository)
            every { notificationRuleRepository.addVehicleStateMatchForRule(any(), any()) } just Runs

            val notificationData = getTestNotificationDataModel()
            notificationService.storeNotificationData(notificationData)

            verify(exactly = notificationData.matchedVehicleStates.size) {
                notificationRuleRepository.addVehicleStateMatchForRule(any(), any())
            }
        }
    }
}
