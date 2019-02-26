package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.Date
import javax.annotation.Resource

@Component
class NotificationTextService : INotificationTextService {

    @Resource(name = "notificationTemplateEngine")
    lateinit var templateEngine: TemplateEngine

    override fun getHtmlMailTextForNotification(notificationData: NotificationData): String {

        logger.info("Generating text for rule with ID {}", notificationData.notificationRule.ruleId)

        val locale = notificationData.notificationRule.owner!!.userSettings!!.locale!!.localeFormat

        val context = Context(locale)

        context.setVariable("name", notificationData.notificationRule.owner!!.name!!)
        context.setVariable("rule", notificationData.notificationRule.name)
        context.setVariable("hobbies", listOf("Cinema", "Sports", "Music"))

        return templateEngine.process(HTML_MAIL_TEMPLATE, context)
    }

    override fun getSmsTextForNotification(notificationData: NotificationData): String {

        logger.info("Generating text for rule with ID {}", notificationData.notificationRule.ruleId)

        val locale = notificationData.notificationRule.owner!!.userSettings!!.locale!!.localeFormat

        val context = Context(locale)

        context.setVariable("name", notificationData.notificationRule.owner!!.name!!)
        context.setVariable("subscriptionDate", Date())

        val allVehicleReferences = notificationData.matchedVehicleStates.map { vehicleState ->

            val brand = vehicleState.vehicleReference?.brand
            val series = vehicleState.vehicleReference?.series
            val model = vehicleState.vehicleReference?.model
            val vin = vehicleState.vehicleReference?.vin

            "$brand - $series - $model: $vin"
        }.toSet()

        context.setVariable("vehicles", listOf(allVehicleReferences))

        return templateEngine.process(TEXT_SMS_TEMPLATE, context)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationTextService::class.java)

        const val HTML_MAIL_TEMPLATE = "notification-mail"

        const val TEXT_SMS_TEMPLATE = "notification-sms"
    }
}
