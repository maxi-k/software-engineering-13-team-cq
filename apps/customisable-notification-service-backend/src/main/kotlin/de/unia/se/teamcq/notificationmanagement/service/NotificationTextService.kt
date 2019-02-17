package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.Date
import java.util.Locale

@Component
class NotificationTextService : INotificationTextService {

    @Autowired
    lateinit var templateEngine: TemplateEngine

    override fun getHtmlMailTextForNotification(notificationData: NotificationData): String {

        logger.info("Generating text for rule with ID {}", notificationData.notificationRule.ruleId)

        val locale = Locale.ENGLISH

        val context = Context(locale)

        context.setVariable("name", notificationData.notificationRule.owner!!.name!!)
        context.setVariable("subscriptionDate", Date())
        context.setVariable("hobbies", listOf("Cinema", "Sports", "Music"))

        // Create the HTML body using Thymeleaf
        return this.templateEngine.process(HTML_MAIL_TEMPLATE, context)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationTextService::class.java)

        const val HTML_MAIL_TEMPLATE = "notification-mail"
    }
}
