package de.unia.se.teamcq.rule.management.controller

import de.unia.se.teamcq.rule.management.dto.NotificationRuleDto
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.DeleteMapping
import javax.validation.Valid

@RestController
@RequestMapping("/notification-rule-management")
interface NotificationRuleController {
    @GetMapping("/notification-rule")
    fun getNotificationRules(): List<NotificationRuleDto>

    @GetMapping("/notification-rule/{id}")
    fun getNotificationRule(@PathVariable(value = "id") id: Long): NotificationRuleDto

    @PostMapping("/notification-rule")
    fun createNotificationRule(@Valid @RequestBody notificationRule: NotificationRuleDto): NotificationRuleDto

    @PutMapping("/notification-rule/{id}")
    fun updateNotificationRule(
        @PathVariable(value = "id") id: Long,
        @Valid @RequestBody notificationRule: NotificationRuleDto
    ): NotificationRuleDto

    @DeleteMapping("/notification-rule/{id}")
    fun deleteNotificationRule(@PathVariable(value = "id") id: Long): NotificationRuleDto
}
