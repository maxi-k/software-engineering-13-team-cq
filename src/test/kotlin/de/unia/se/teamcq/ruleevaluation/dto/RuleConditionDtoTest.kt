package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.TestUtils.getTestRuleConditionDto
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionDtoTest : StringSpec() {

    init {
        "Equal and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionDto,
                    { it.conditionId = 1 }
            )
        }


    }
}
