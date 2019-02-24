package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Repository

@Repository
interface INotificationRuleRepository {

    fun getAllNotificationRulesForUser(username: String): List<NotificationRule>

    fun getNotificationRule(ruleId: Long): NotificationRule?

    fun createNotificationRule(notificationRule: NotificationRule): NotificationRule?

    fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule?

    fun deleteNotificationRule(ruleId: Long)

    fun getVehicleStateMatchesForRule(ruleId: Long): List<VehicleState>

    fun addVehicleStateMatchForRule(ruleId: Long, vehicleState: VehicleState)

    fun deleteAllVehicleStateMatchesForRule(ruleId: Long)
}
