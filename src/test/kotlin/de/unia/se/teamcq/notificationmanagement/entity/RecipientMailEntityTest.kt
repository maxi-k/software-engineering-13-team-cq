package de.unia.se.teamcq.notificationmanagement.entity

import de.unia.se.teamcq.TestUtils.getTestRecipientMailEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RecipientMailEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRecipientMailEntity,
                    { it.recipientId = 1 },
                    { it.mailAddress = "test@example.de" }
            )
        }
    }
}
