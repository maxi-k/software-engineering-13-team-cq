package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.model.VehicleState

class NotificationData(

    val triggeringVehicleState: VehicleState,

    val notificationRule: NotificationRule

) : INotificationData {

    override fun getNoficationName(): String {
        return notificationRule.name!!
    }
}
