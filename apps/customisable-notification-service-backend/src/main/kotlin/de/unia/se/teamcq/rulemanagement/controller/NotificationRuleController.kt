package de.unia.se.teamcq.rulemanagement.controller

import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import de.unia.se.teamcq.user.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/notification-rule-management")
class NotificationRuleController {

    @Autowired
    lateinit var notificationRuleService: INotificationRuleService

    @Autowired
    lateinit var notificationRuleMapper: INotificationRuleMapper

    @Autowired
    lateinit var userService: IUserService

    @GetMapping("/notification-rule")
    fun getNotificationRules(): List<NotificationRuleDto> {
        val username = SecurityContextHolder.getContext().authentication.name

        val notificationRules = notificationRuleService.getNotificationRulesForUser(username)

        return notificationRules
                .map { notificationRule -> notificationRuleMapper.modelToDto(notificationRule) }
    }

    @GetMapping("/notification-rule/{ruleId}")
    fun getNotificationRule(@PathVariable(value = "ruleId") ruleId: Long): ResponseEntity<NotificationRuleDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRuleIfPresent = notificationRuleService.getNotificationRule(ruleId)

        notificationRuleIfPresent?.owner?.let { owner ->
            if (owner.name != username) {
                return ResponseEntity.badRequest().build()
            }
        }

        return notificationRuleIfPresent?.let { notificationRule ->
            ResponseEntity.ok(notificationRuleMapper.modelToDto(notificationRule))
        } ?: (ResponseEntity.notFound().build())
    }

    @PostMapping("/notification-rule")
    fun createNotificationRule(@Valid @RequestBody notificationRuleDto: NotificationRuleDto): ResponseEntity<NotificationRuleDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userService.getOrCreateUser(username)

        return try {
            val notificationRuleToCreate = notificationRuleMapper.dtoToModel(notificationRuleDto).copy(owner = user)
            val notificationRuleIfCreated = notificationRuleService.createNotificationRule(username, notificationRuleToCreate)

            notificationRuleIfCreated?.let { notificationRule ->
                ResponseEntity.ok(notificationRuleMapper.modelToDto(notificationRule))
            } ?: (ResponseEntity.notFound().build())
        } catch (illegalArgumentException: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/notification-rule/{ruleId}")
    fun updateNotificationRule(
        @PathVariable(value = "ruleId") ruleId: Long,
        @Valid @RequestBody notificationRuleDto: NotificationRuleDto
    ): ResponseEntity<NotificationRuleDto> {

        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRuleIfPresent = notificationRuleService.getNotificationRule(ruleId)

        if (notificationRuleIfPresent?.owner?.name != notificationRuleDto.owner?.name) {
            return ResponseEntity.badRequest().build()
        }

        if (notificationRuleIfPresent?.owner?.name != username) {
            return ResponseEntity.badRequest().build()
        }

        return try {
            val notificationRuleToCreate = notificationRuleMapper.dtoToModel(notificationRuleDto)
            val notificationRuleIfUpdated = notificationRuleService.updateNotificationRule(notificationRuleToCreate)

            return notificationRuleIfUpdated?.let { notificationRule ->
                ResponseEntity.ok(notificationRuleMapper.modelToDto(notificationRule))
            } ?: (ResponseEntity.notFound().build())
        } catch (illegalArgumentException: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/notification-rule/{ruleId}")
    fun deleteNotificationRule(@PathVariable(value = "ruleId") ruleId: Long): ResponseEntity<Any> {
        val username = SecurityContextHolder.getContext().authentication.name
        val notificationRuleIfPresent = notificationRuleService.getNotificationRule(ruleId)

        return if (notificationRuleIfPresent?.owner?.name != username) {
            ResponseEntity.badRequest().build()
        } else {
            notificationRuleService.deleteNotificationRule(ruleId)
            ResponseEntity.ok().build()
        }
    }
}
