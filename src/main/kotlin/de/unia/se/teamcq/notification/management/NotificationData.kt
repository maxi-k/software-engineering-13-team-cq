package de.unia.se.teamcq.notification.management

import de.unia.se.teamcq.events.model.VehicleStatus

interface NotificationData {

    val trigger: VehicleStatus
}
