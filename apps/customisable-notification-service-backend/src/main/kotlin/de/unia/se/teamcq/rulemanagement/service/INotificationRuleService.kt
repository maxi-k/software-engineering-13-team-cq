package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.stereotype.Service

/**
 * The central CRUD service that delegates tasks such as scheduling new [NotificationRule]s.
 */
@Service
interface INotificationRuleService {

    /**
     * Get all NotificationRules for a User
     *
     * @param username The username of the owner of the [NotificationRule]s
     * @returns All [NotificationRule]s of the user
     */
    fun getNotificationRulesForUser(username: String): List<NotificationRule>

    /**
     * Get a [NotificationRule] by ID
     *
     * @param ruleId The ID of the [NotificationRule]
     * @returns The [NotificationRule] if it exists
     */
    fun getNotificationRule(ruleId: Long): NotificationRule?

    /**
     * Create a [NotificationRule]
     *
     * @param username The owner of the [NotificationRule] to create
     * @param notificationRule The [NotificationRule] to create
     * @returns The [NotificationRule] if creating it worked
     */
    fun createNotificationRule(username: String, notificationRule: NotificationRule): NotificationRule?

    /**
     * Update a [NotificationRule]
     *
     * @param username The [NotificationRule] to update
     * @returns The updated [NotificationRule] if it exists
     */
    fun updateNotificationRule(notificationRule: NotificationRule): NotificationRule?

    /**
     * Delete a [NotificationRule] by ID
     *
     * @param ruleId The ID of the [NotificationRule] to delete
     */
    fun deleteNotificationRule(ruleId: Long)
}
