package de.unia.se.teamcq.notificationmanagement.dto

import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class AggregatorCountingDtoTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestAggregatorCountingDto,
                    { it.aggregatorId = 1 },
                    { it.notificationCountThreshold = 20 }
            )
        }
    }
}
