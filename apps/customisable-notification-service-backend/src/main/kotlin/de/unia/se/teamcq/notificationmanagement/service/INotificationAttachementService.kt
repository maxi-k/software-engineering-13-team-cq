package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

/**
 * A service to manage generating attachments for [NotificationRule]s notification.
 */
@Service
interface INotificationAttachmentService {

    /**
     * Generate a csv attachment for a Notification
     *
     * @param notificationData The [NotificationData], containing all required information
     */
    fun getCsvAttachment(notificationData: NotificationData): Resource
}