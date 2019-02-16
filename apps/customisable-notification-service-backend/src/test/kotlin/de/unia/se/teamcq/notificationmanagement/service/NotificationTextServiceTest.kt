package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationTextServiceTest : StringSpec() {

    @Autowired
    private lateinit var notificationTextService: NotificationTextService

    init {

        "GetTextForNotification should work" {
            val notificationData = getTestNotificationDataModel()
            notificationTextService.getHtmlMailTextForNotification(notificationData)
        }
    }
}
