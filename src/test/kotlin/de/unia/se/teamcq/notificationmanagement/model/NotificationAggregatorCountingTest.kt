package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAggregatorCountingTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestNotificationAggregatorCountingModel,
                    { it.aggregatorId = 1 },
                    { it.notificationCountThreshold = 20 }
            )
        }
    }
}
