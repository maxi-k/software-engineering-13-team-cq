package de.unia.se.teamcq.notificationmanagement.dto

import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediateDto
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class AggregatorImmediateDtoTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestAggregatorImmediateDto,
                    { it.aggregatorId = 1 }
            )
        }
    }
}
