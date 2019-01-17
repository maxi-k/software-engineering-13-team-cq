package de.unia.se.teamcq.notificationmanagement.dto

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAggregatorScheduledDtoTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestNotificationAggregatorScheduledDto,
                    { it.aggregatorId = 1 },
                    { it.notificationCronTrigger = "0 0 10 * * TUE" }
            )
        }
    }
}
