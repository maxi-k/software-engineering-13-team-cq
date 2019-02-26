package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.mes.ApiClient
import de.bmw.mes.api.EmailV1Api
import de.bmw.mes.model.Attachement
import de.bmw.mes.model.RequestFreeTextMessageEmail
import de.unia.se.teamcq.security.service.IAuthenticationTokenAdapter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import org.springframework.web.client.RestClientException
import java.util.UUID

@Component
class NotificationMESAdapter : INotificationMESAdapter {

    @Value("\${de.unia.se.teamcq.disable-notifications:true}")
    private var disableNotifications: Boolean? = null

    @Autowired
    private lateinit var authenticationTokenAdapter: IAuthenticationTokenAdapter

    @Throws(RestClientException::class, NullPointerException::class)
    override fun sendNotification(
        receiver: String,
        subject: String,
        body: String,
        resource: Resource
    ) {

        if (disableNotifications == true) {
            logger.info("Sending notification to $receiver")
        } else {

            val (header, token) = authenticationTokenAdapter.getAuthenticationHeader()

            val apiInstance = EmailV1Api()
            val apiClient = ApiClient()
            apiClient.addDefaultHeader(header, token)
            apiInstance.apiClient = apiClient

            val requestFreeTextMessageEmail = RequestFreeTextMessageEmail()
            requestFreeTextMessageEmail.receiver = receiver
            requestFreeTextMessageEmail.sender = SENDER
            requestFreeTextMessageEmail.subject = subject
            requestFreeTextMessageEmail.body = body

            val attachment = Attachement()
            attachment.content = FileCopyUtils.copyToString(resource.inputStream.bufferedReader())
            attachment.filename = FILENAME
            attachment.mimeType = MIME_TYPE

            requestFreeTextMessageEmail.attachement = listOf(attachment)

            val uuidForLogging = UUID.randomUUID().toString()

            val sendMailResult = apiInstance.sendFreeTextEmailMessage(requestFreeTextMessageEmail, uuidForLogging)
            logger.info("Sending E-Mail successful!", sendMailResult)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationMESAdapter::class.java)
        private const val SENDER = "noreply@cns.bmw.de"
        private const val FILENAME = "vehicles.csv"
        private const val MIME_TYPE = "text/csv"
    }
}
