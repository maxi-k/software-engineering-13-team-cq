package de.unia.se.teamcq.notification.rule.management.controller

import de.unia.se.teamcq.notification.rule.management.model.NotificationRule
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/notification-rule-management")
interface NotificationRuleController {
    @GetMapping("/notification-rule")
    fun getNotificationRules(): List<NotificationRule>

    @GetMapping("/notification-rule/{id}")
    fun getNotificationRule(@PathVariable(value = "id") id: Long): NotificationRule

    @PostMapping("/notification-rule")
    fun createNotificationRule(@Valid @RequestBody notificationRule: NotificationRule): NotificationRule

    @PutMapping("/notification-rule/{id}")
    fun updateNotificationRule(
        @PathVariable(value = "id") id: Long,
        @Valid @RequestBody notificationRule: NotificationRule
    ): NotificationRule

    @DeleteMapping("/notification-rule/{id}")
    fun deleteNotificationRule(@PathVariable(value = "id") id: Long): NotificationRule
}
