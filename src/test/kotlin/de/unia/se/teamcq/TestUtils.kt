package de.unia.se.teamcq

import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.rulemanagement.model.NotificationRuleBuilder
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.dto.UserSettingsDto
import de.unia.se.teamcq.user.entity.UserEntity
import de.unia.se.teamcq.user.entity.UserSettingsEntity
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.model.UserNotificationType
import de.unia.se.teamcq.user.model.UserSettings
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

object TestUtils {

    fun buildMockMvc(webApplicationContext: WebApplicationContext): MockMvc {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    fun getTestNotificationRuleModel(): NotificationRule {
        return NotificationRuleBuilder()
                .withId(42)
                .withName("rule_name")
                .withOwner(User(name = "Max Mustermann", mailAddress = "test@example.de", cellPhoneNumber = "1",
                        userSettings = UserSettings(UserNotificationType.EMAIL)))
                .withDescription("description")
                .build()
    }

    fun getTestNotificationRuleDto(): NotificationRuleDto {
        return NotificationRuleDto(
                id = 42,
                name = "rule_name",
                owner = UserDto(name = "Max Mustermann", mailAddress = "test@example.de", cellPhoneNumber = "1", userSettings = UserSettingsDto(UserNotificationType.EMAIL)),
                description = "description"
        )
    }

    fun getTestNotificationRuleEntity(): NotificationRuleEntity {
        return NotificationRuleEntity(
                id = 42,
                name = "rule_name",
                owner = UserEntity(name = "Max Mustermann", mailAddress = "test@example.de", cellPhoneNumber = "1", userSettings = UserSettingsEntity(0, UserNotificationType.EMAIL), notificationRules = null),
                description = "description"
        )
    }
}
