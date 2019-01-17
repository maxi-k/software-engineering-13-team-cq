package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.TestUtils.getTestRuleConditionEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionEntity,
                    { it.conditionId = 1 }
            )
        }
    }
}
