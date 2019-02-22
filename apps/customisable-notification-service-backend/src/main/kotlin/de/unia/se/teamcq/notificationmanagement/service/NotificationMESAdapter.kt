package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.authentication.*
import de.bmw.authentication.auth.*
import de.bmw.authentication.model.*
import de.bmw.authentication.api.LoginApi


class NotificationMESAdapter : INotificationMESAdapter {

    override fun sendNotification() {
        val apiInstance = LoginApi()
        val username = "username_example" // String |
        val password = "password_example" // String |
        try {
            val result = apiInstance.login(username, password)
            System.out.println(result)
        } catch (e: ApiException) {
            System.err.println("Exception when calling LoginApi#login")
            e.printStackTrace()
        }

    }
}
