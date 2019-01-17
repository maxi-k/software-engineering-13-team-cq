package de.unia.se.teamcq.ruleevaluation.model

import de.unia.se.teamcq.TestUtils.getTestRuleConditionModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionTest : StringSpec() {

    init {
        "Equal and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionModel,
                    { it.conditionId = 1 }
            )
        }


    }
}
