package de.unia.se.teamcq.notificationmanagement.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

/**
 * The service that handles sending Notifications.
 */
@Service
interface INotificationMESAdapter {

    /**
     * Send a notification
     *
     * Sends the notification to the BMW Messaging Service
     */
    @Throws(RestClientException::class, NullPointerException::class)
    fun sendNotification()
}
