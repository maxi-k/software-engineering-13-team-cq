package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
<<<<<<< HEAD
=======
import io.mockk.impl.annotations.MockK
>>>>>>> master
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationServiceTest : StringSpec() {

<<<<<<< HEAD
=======
    @MockK
    private lateinit var notificationTextService: INotificationTextService

>>>>>>> master
    @InjectMockKs
    private lateinit var notificationService: NotificationService

    init {
        MockKAnnotations.init(this)

        "SendNotificationForScheduledRule should work" should {
            val notificationRule = getTestNotificationRuleModel()
            notificationService.sendNotificationForNonScheduledRule(notificationRule)
        }

        "SendNotificationForNonScheduledRule should work" should {
            val notificationRule = getTestNotificationRuleModel()
            notificationService.sendNotificationForNonScheduledRule(notificationRule)
        }

        "StoreNotificationData should work" should {
            val notificationData = getTestNotificationDataModel()
            notificationService.storeNotificationData(notificationData)
        }
    }
}
