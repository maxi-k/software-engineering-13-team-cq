package de.unia.se.teamcq.notificationmanagement.entity

import de.unia.se.teamcq.TestUtils.getTestRecipientSmsEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RecipientSmsEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRecipientSmsEntity,
                    { it.recipientId = 1 },
                    { it.phoneNumber = "2345678" }
            )
        }
    }
}
