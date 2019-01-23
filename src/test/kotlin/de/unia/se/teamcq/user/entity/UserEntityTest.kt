package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import de.unia.se.teamcq.user.model.UserNotificationType
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class UserEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestUserEntity,
                    { it.name = "test" },
                    { it.mailAddress = "test" },
                    { it.cellPhoneNumber = "test" },
                    { it.userSettings = UserSettingsEntity(1, UserNotificationType.SMS) }
                    // Not notificationRules, otherwise that would lead to cyclic referencing
            )
        }
    }
}
