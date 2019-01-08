package de.unia.se.teamcq.rulemanagement.controller

import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.mapping.NotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
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
class NotificationRuleController(private val notificationRuleService: INotificationRuleService) {

    @Autowired
    lateinit var notificationRuleMapper: NotificationRuleMapper

    @GetMapping("/notification-rule")
    fun getNotificationRules(): List<NotificationRuleDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRules = notificationRuleService.getNotificationRulesForUser(username)

        return notificationRules
                .map { notificationRule -> notificationRuleMapper.modelToDto(notificationRule) }
    }

    @GetMapping("/notification-rule/{ruleId}")
    fun getNotificationRule(@PathVariable(value = "ruleId") ruleId: Long): ResponseEntity<NotificationRuleDto> {
        // Fixme: Access Control
        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRuleIfPresent = notificationRuleService.getNotificationRule(ruleId)

        return notificationRuleIfPresent.map { notificationRule ->
            ResponseEntity.ok(notificationRuleMapper.modelToDto(notificationRule))
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/notification-rule")
    fun createNotificationRule(@Valid @RequestBody notificationRuleDto: NotificationRuleDto): ResponseEntity<NotificationRuleDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRuleToCreate = notificationRuleMapper.dtoToModel(notificationRuleDto)
        val notificationRuleIfCreated = notificationRuleService.createNotificationRule(username, notificationRuleToCreate)

        return notificationRuleIfCreated.map { notificationRule ->
            ResponseEntity.ok(notificationRuleMapper.modelToDto(notificationRule))
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/notification-rule/{ruleId}")
    fun updateNotificationRule(
        @PathVariable(value = "ruleId") ruleId: Long,
        @Valid @RequestBody notificationRuleDto: NotificationRuleDto
    ): ResponseEntity<NotificationRuleDto> {

        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRuleToCreate = notificationRuleMapper.dtoToModel(notificationRuleDto)
        val notificationRuleIfUpdated = notificationRuleService.updateNotificationRule(ruleId, notificationRuleToCreate)

        return notificationRuleIfUpdated.map { notificationRule ->
            ResponseEntity.ok(notificationRuleMapper.modelToDto(notificationRule))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/notification-rule/{ruleId}")
    fun deleteNotificationRule(@PathVariable(value = "ruleId") ruleId: Long): Boolean {
        val username = SecurityContextHolder.getContext().authentication.name
        return true
    }
}
