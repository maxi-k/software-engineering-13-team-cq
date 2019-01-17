package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAggregatorImmediateTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestNotificationAggregatorImmediatelyModel,
                    { it.aggregatorId = 1 }
            )
        }
    }
}
