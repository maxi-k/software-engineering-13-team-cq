package de.unia.se.teamcq.notificationmanagement.service

import org.springframework.stereotype.Service

@Service
interface INotificationMESAdapter {

    fun sendNotification()
}
