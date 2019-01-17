package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAggregatorTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestNotificationAggregatorModel,
                    { it.aggregatorId = 1 }
            )
        }
    }
}
