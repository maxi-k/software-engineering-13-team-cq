package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.mes.ApiClient
import de.bmw.mes.api.EmailV1Api
import de.bmw.mes.model.RequestFreeTextMessageEmail
import de.unia.se.teamcq.security.service.IAuthenticationTokenService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import java.util.UUID

@Component
class NotificationMESAdapter : INotificationMESAdapter {

    @Value("\${de.unia.se.teamcq.disable-notifications:true}")
    private var disableNotifications: Boolean? = null

    @Autowired
    private lateinit var authenticationTokenService: IAuthenticationTokenService

    @Throws(RestClientException::class, NullPointerException::class)
    override fun sendNotification() {

        if (disableNotifications == true) {
            logger.info("Sending notification")
        } else {

            val (header, token) = authenticationTokenService.getAuthenticationHeader()

            val apiInstance = EmailV1Api()
            val apiClient = ApiClient()
            apiClient.addDefaultHeader(header, token)
            apiInstance.apiClient = apiClient

            val requestFreeTextMessageEmail = RequestFreeTextMessageEmail()
            requestFreeTextMessageEmail.receiver = "test"
            requestFreeTextMessageEmail.sender = "test"
            requestFreeTextMessageEmail.subject = "test"
            requestFreeTextMessageEmail.body = ""
            requestFreeTextMessageEmail.attachement = listOf()

            val uuidForLogging = UUID.randomUUID().toString()

            val sendMailResult = apiInstance.sendFreeTextEmailMessage(requestFreeTextMessageEmail, uuidForLogging)
            logger.info("Sending E-Mail successful!", sendMailResult)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationMESAdapter::class.java)
    }
}
