package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.authentication.api.LoginApi

import java.lang.Exception

class NotificationMESAdapter : INotificationMESAdapter {

    override fun sendNotification() {
        val apiInstance = LoginApi()
        val username = "username_example" // String |
        val password = "password_example" // String |
        try {
            val result = apiInstance.login(username, password)
            System.out.println(result)
        } catch (e: Exception) {
            System.err.println("Exception when calling LoginApi#login")
            e.printStackTrace()
        }
    }
}
