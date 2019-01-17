package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.scheduling.support.CronTrigger
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAggregatorScheduledTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestNotificationAggregatorScheduledModel,
                    { it.aggregatorId = 1 },
                    { it.notificationCronTrigger = CronTrigger("0 0 10 * * TUE") }
            )
        }
    }
}
