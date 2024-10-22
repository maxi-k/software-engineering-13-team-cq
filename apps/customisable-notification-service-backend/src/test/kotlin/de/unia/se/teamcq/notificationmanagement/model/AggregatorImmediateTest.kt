package de.unia.se.teamcq.notificationmanagement.model

import de.unia.se.teamcq.TestUtils.getAggregatorImmediateModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class AggregatorImmediateTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getAggregatorImmediateModel,
                    { it.aggregatorId = 1 }
            )
        }
    }
}
