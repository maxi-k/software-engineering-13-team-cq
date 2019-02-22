package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.authentication.api.LoginApi
import de.bmw.authentication.model.Login
import de.bmw.mes.ApiClient
import de.bmw.mes.api.EmailV1Api
import de.bmw.mes.model.RequestFreeTextMessageEmail
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import java.lang.Exception
import java.util.UUID

@Component
class NotificationMESAdapter : INotificationMESAdapter {

    @Value("\${de.unia.se.teamcq.disable-notifications:true}")
    private var disableNotifications: Boolean? = null

    @Value("\${de.unia.se.teamcq.bmw-authentication.username:name}")
    private lateinit var authenticationUsername: String

    @Value("\${de.unia.se.teamcq.bmw-authentication.password:pw}")
    private lateinit var authenticationPassword: String

    override fun sendNotification() {

        if (disableNotifications == true) {
            logger.info("Sending notification")
        } else {

            val loginApi = LoginApi()
            var loginResult: Login? = null
            try {
                loginResult = loginApi.login(authenticationUsername, authenticationPassword)
                logger.info("Login successful", loginResult.toString())
            } catch (exception: Exception) {
                logger.error("Exception when calling LoginApi#login", exception)
            }

            val apiInstance = EmailV1Api()
            val apiClient = ApiClient()
            apiClient.setAccessToken(loginResult?.accessToken)
            apiInstance.apiClient = apiClient

            val requestFreeTextMessageEmail = RequestFreeTextMessageEmail()
            requestFreeTextMessageEmail.receiver = "test"
            requestFreeTextMessageEmail.sender = "test"
            requestFreeTextMessageEmail.subject = "test"
            requestFreeTextMessageEmail.body = ""
            requestFreeTextMessageEmail.attachement = listOf()

            val uuidForLogging = UUID.randomUUID().toString()

            try {
                val sendMailResult = apiInstance.sendFreeTextEmailMessage(requestFreeTextMessageEmail, uuidForLogging)
                logger.info("Sending E-Mail successful!", sendMailResult)
            } catch (exception: Exception) {
                logger.error("Exception when calling EmailV1Api#getEmailMessageTemplate", exception)
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationMESAdapter::class.java)
    }
}
