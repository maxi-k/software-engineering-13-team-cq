package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import de.unia.se.teamcq.user.model.UserLocale
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationTextServiceTest : StringSpec() {

    @Autowired
    private lateinit var notificationTextService: NotificationTextService

    init {

        "GetHtmlMailTextForNotification" should {

            "Generate Html Notification text using Locale.EN" {

                val notificationData = getTestNotificationDataModel().apply {
                    notificationRule.owner!!.userSettings!!.locale = UserLocale.EN
                }
                val htmlText = notificationTextService.getHtmlMailTextForNotification(notificationData)

                val vehicleVin = notificationData.matchedVehicleStates.map { vehicleState ->
                    vehicleState.vehicleReference!!.vin!!
                }.sorted().first()

                htmlText.shouldContain(vehicleVin)
                htmlText.shouldContain("Hello")
            }

            "Generate Html Notification text using Locale.DE" {

                val notificationData = getTestNotificationDataModel().apply {
                    notificationRule.owner!!.userSettings!!.locale = UserLocale.DE
                }
                val htmlText = notificationTextService.getHtmlMailTextForNotification(notificationData)

                val vehicleVin = notificationData.matchedVehicleStates.map { vehicleState ->
                    vehicleState.vehicleReference!!.vin!!
                }.sorted().first()

                htmlText.shouldContain(vehicleVin)
                htmlText.shouldContain("Hi")
            }
        }

        "GetSmsTextForNotification should work" should {

            "Generate SMS Notification text using Locale.EN" {

                val notificationData = getTestNotificationDataModel().apply {
                    notificationRule.owner!!.userSettings!!.locale = UserLocale.EN
                }
                val smsText = notificationTextService.getSmsTextForNotification(notificationData)

                val vehicleVin = notificationData.matchedVehicleStates.map { vehicleState ->
                    vehicleState.vehicleReference!!.vin!!
                }.sorted().first()

                smsText.shouldContain(vehicleVin)
                smsText.shouldContain("Hello")
            }

            "Generate SMS Notification text using Locale.DE" {

                val notificationData = getTestNotificationDataModel().apply {
                    notificationRule.owner!!.userSettings!!.locale = UserLocale.DE
                }
                val smsText = notificationTextService.getSmsTextForNotification(notificationData)

                val vehicleVin = notificationData.matchedVehicleStates.map { vehicleState ->
                    vehicleState.vehicleReference!!.vin!!
                }.sorted().first()

                smsText.shouldContain(vehicleVin)
                smsText.shouldContain("Hi")
            }
        }
    }
}
