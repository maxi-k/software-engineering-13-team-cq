package de.unia.se.teamcq.notificationmanagement.entity

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAggregatorImmediateEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestNotificationAggregatorImmediatelyEntity,
                    { it.aggregatorId = 1 }
            )
        }
    }
}
