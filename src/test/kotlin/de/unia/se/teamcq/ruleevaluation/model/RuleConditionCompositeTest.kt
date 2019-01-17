package de.unia.se.teamcq.ruleevaluation.model

import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionCompositeTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionCompositeModel,
                    { it.conditionId = 1 },
                    { it.logicalConnective = LogicalConnectiveType.ANY },
                    { it.subConditions = emptyList() }
            )
        }
    }
}
