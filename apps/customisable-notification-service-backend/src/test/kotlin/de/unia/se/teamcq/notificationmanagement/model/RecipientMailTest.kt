package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.TestUtils.getTestRecipientMailModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RecipientMailTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRecipientMailModel,
                    { it.recipientId = 1 },
                    { it.mailAddress = "test@example.de" }
            )
        }
    }
}
