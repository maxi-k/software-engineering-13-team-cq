package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationTextServiceTest : StringSpec() {

    @Autowired
    private lateinit var notificationTextService: NotificationTextService

    init {

        "GetHtmlMailTextForNotification should work" {
            val notificationData = getTestNotificationDataModel()
            val htmlText = notificationTextService.getHtmlMailTextForNotification(notificationData)

            htmlText.shouldContain(notificationData.notificationRule.owner!!.name!!)
        }

        "GetSmsTextForNotification should work" {
            val notificationData = getTestNotificationDataModel()
            val smsText = notificationTextService.getSmsTextForNotification(notificationData)

            smsText.shouldContain(notificationData.notificationRule.owner!!.name!!)
        }
    }
}
