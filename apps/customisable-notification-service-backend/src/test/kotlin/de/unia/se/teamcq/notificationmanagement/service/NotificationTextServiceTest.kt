package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationTextServiceTest : StringSpec() {

    @InjectMockKs
    private lateinit var notificationTextService: NotificationTextService

    init {
        MockKAnnotations.init(this)

        "GetTextForNotification should work" should {
            val notificationData = getTestNotificationDataModel()
            notificationTextService.getTextForNotification(notificationData)
        }
    }
}
