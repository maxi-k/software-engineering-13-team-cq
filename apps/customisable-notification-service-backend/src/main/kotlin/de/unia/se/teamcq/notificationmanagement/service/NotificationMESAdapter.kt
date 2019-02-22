package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.authentication.api.LoginApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import java.lang.Exception

@Component
class NotificationMESAdapter : INotificationMESAdapter {

    @Value("\${de.unia.se.teamcq.bmw-authentication.username:name}")
    private lateinit var authenticationUsername: String

    @Value("\${de.unia.se.teamcq.bmw-authentication.password:pw}")
    private lateinit var authenticationPassword: String

    override fun sendNotification() {
        val apiInstance = LoginApi()
        try {
            val result = apiInstance.login(authenticationUsername, authenticationPassword)
            System.out.println(result)
        } catch (e: Exception) {
            System.err.println("Exception when calling LoginApi#login")
            e.printStackTrace()
        }
    }
}
