package de.unia.se.teamcq.notificationmanagement.service

import de.bmw.mes.model.Attachement
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
     *
     * @param receiver The receiver email address
     * @param subject The email subject
     * @param body The email body
     * @param attachment The email attachment
     */
    @Throws(RestClientException::class, NullPointerException::class)
    fun sendNotification(
        receiver: String,
        subject: String,
        body: String,
        attachment: List<Attachement>
    )
}
