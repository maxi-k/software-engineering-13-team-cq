package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.model.VehicleState

class NotificationData(

    val notificationRule: NotificationRule,

    val matchedVehicleStates: Set<VehicleState>

) : INotificationData {

    override fun getNoficationName(): String {
        return notificationRule.name!!
    }
}
